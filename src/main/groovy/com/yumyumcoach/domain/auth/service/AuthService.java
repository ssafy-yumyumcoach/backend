package com.yumyumcoach.domain.auth.service;

import com.yumyumcoach.domain.auth.dto.LoginRequest;
import com.yumyumcoach.domain.auth.dto.LoginResponse;
import com.yumyumcoach.domain.auth.dto.UserInfo;
import com.yumyumcoach.domain.auth.entity.Account;
import com.yumyumcoach.domain.auth.mapper.AccountMapper;
import com.yumyumcoach.global.exception.BusinessException;
import com.yumyumcoach.global.exception.ErrorCode;
import com.yumyumcoach.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
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

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType(jwtTokenProvider.getTokenType())
                .accessTokenExpiresIn(jwtTokenProvider.getAccessTokenExpirationSeconds())
                . refreshTokenExpiresIn(jwtTokenProvider.getRefreshTokenExpirationSeconds())
                .userInfo(new UserInfo(account.getEmail(), account.getUsername()))
                .build();
    }
}
