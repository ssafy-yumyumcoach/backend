package com.yumyumcoach.domain.auth.service;

import com.yumyumcoach.domain.auth.dto.LoginRequest;
import com.yumyumcoach.domain.auth.dto.LoginResponse;
import com.yumyumcoach.domain.auth.dto.UserInfo;
import com.yumyumcoach.domain.auth.entity.Account;
import com.yumyumcoach.domain.auth.entity.RefreshToken;
import com.yumyumcoach.domain.auth.mapper.AccountMapper;
import com.yumyumcoach.domain.auth.mapper.RefreshTokenMapper;
import com.yumyumcoach.global.exception.BusinessException;
import com.yumyumcoach.global.exception.ErrorCode;
import com.yumyumcoach.global.jwt.JwtTokenProvider;
import com.yumyumcoach.global.jwt.TokenHashUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

/*
사용자에게 입력받은 이메일과 비밀번호를 검증하고,
성공 시 JWT 방식의 access token 과 refresh token 생성 후

성공 시: 로그인 정보 반환
실패 시: BusinessException 예외 던짐
 */

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenMapper refreshTokenMapper;
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    @Transactional(readOnly = true)
    public boolean isEmailAvailable(String email) {
        validateEmail(email);
        return !accountMapper.existsByEmail(email);
    }

    @Transactional(readOnly = false)
    public LoginResponse login(LoginRequest request) {
        Account account = accountMapper.findByEmail(request.getEmail());

        // 해당 이메일이 DB 에 없을 때
        if (account == null) {
            throw new BusinessException(ErrorCode.AUTH_INVALID_CREDENTIALS, "등록되지 않은 회원입니다.");
        }
        // 이메일은 DB 에 존재하나 비밀번호가 틀렸을 때
        else if(!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new BusinessException(ErrorCode.AUTH_INVALID_CREDENTIALS, "비밀번호가 일치하지 않습니다.");
        }

        //access token 생성, refresh token 생성
        String accessToken = jwtTokenProvider.createAccessToken(account.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshToken(account.getEmail());

        saveRefreshToken(account.getEmail(), refreshToken);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType(jwtTokenProvider.getTokenType())
                .accessTokenExpiresIn(jwtTokenProvider.getAccessTokenExpirationSeconds())
                . refreshTokenExpiresIn(jwtTokenProvider.getRefreshTokenExpirationSeconds())
                .userInfo(new UserInfo(account.getEmail(), account.getUsername()))
                .build();
    }

    @Transactional
    public void logout(String authenticatedEmail, String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new BusinessException(ErrorCode.AUTH_REFRESH_TOKEN_REQUIRED);
        }

        jwtTokenProvider.validateToken(refreshToken);
        String emailFromToken = jwtTokenProvider.getEmail(refreshToken);

        if (authenticatedEmail == null || !authenticatedEmail.equals(emailFromToken)) {
            throw new BusinessException(ErrorCode.AUTH_INVALID_REFRESH_TOKEN, "사용자 정보가 일치하지 않습니다.");
        }

        String tokenHash = TokenHashUtil.sha256Hex(refreshToken);

        int deleted = refreshTokenMapper.deleteByEmailAndHash(emailFromToken, tokenHash);
        if (deleted == 0) {
            throw new BusinessException(ErrorCode.AUTH_INVALID_REFRESH_TOKEN);
        }
    }

    private void saveRefreshToken(String email, String refreshToken) {
        String tokenHash = TokenHashUtil.sha256Hex(refreshToken);
        LocalDateTime expiresAt = LocalDateTime.now()
                .plusSeconds(jwtTokenProvider.getRefreshTokenExpirationSeconds());
        refreshTokenMapper.upsert(email, tokenHash, expiresAt);
    }

    private static void validateEmail(String email) {
        if (email == null || email.isBlank() || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new BusinessException(ErrorCode.AUTH_INVALID_EMAIL_FORMAT);
        }
    }
}
