package com.yumyumcoach.global.jwt;

import com.yumyumcoach.global.exception.BusinessException;
import com.yumyumcoach.global.exception.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    /**
     * 핵심: 필터에서 발생한 예외를 @RestControllerAdvice(GlobalExceptionHandler) 쪽으로 넘겨서
     *      ErrorResponse 형태로 통일된 JSON 응답을 내려주기 위해 사용한다.
     */
    private final HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 0) 인증이 필요 없는 경로면 그냥 통과 (로그인, 회원가입 등)
            if (request.getRequestURI().startsWith("/api/auth")) {
                filterChain.doFilter(request, response);
                return;
            }

            // 1) Authorization 헤더에서 Bearer 토큰 추출
            String token = null;
            String header = request.getHeader("Authorization");
            if (header != null && header.startsWith("Bearer ")) {
                token = header.substring(7);
            }

            // 2) 토큰이 없으면... 인증 불가(401)
            if (token == null) {
                resolver.resolveException(request, response, null, new BusinessException(ErrorCode.AUTH_UNAUTHORIZED));
                return;
            }

            // 3) 토큰 검증
            jwtTokenProvider.validateToken(token);

            // 4) 토큰에서 이메일(subject) 추출
            String email = jwtTokenProvider.getEmail(token);

            // 5) SecurityContext에 인증 정보 세팅
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // 6) 다음 필터/컨트롤러로 진행
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            // 만료 토큰
            resolver.resolveException(request, response, null,
                    new BusinessException(ErrorCode.AUTH_UNAUTHORIZED, "액세스 토큰이 만료되었습니다."));
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            // 유효하지 않은 토큰
            resolver.resolveException(request, response, null,
                    new BusinessException(ErrorCode.AUTH_UNAUTHORIZED));
        } finally {
            SecurityContextHolder.clearContext();
        }
    }
}
