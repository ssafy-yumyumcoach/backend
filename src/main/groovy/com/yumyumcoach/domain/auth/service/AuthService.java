package com.yumyumcoach.domain.auth.service;

import com.yumyumcoach.domain.auth.dto.LoginRequest;
import com.yumyumcoach.domain.auth.dto.LoginResponse;
import com.yumyumcoach.domain.auth.dto.UserInfo;
import com.yumyumcoach.domain.auth.dto.SignUpRequest;
import com.yumyumcoach.domain.auth.dto.SignUpResponse;
import com.yumyumcoach.domain.auth.entity.Account;
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

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenMapper refreshTokenMapper;
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("^[가-힣a-zA-Z0-9._]{2,12}$");

    //이메일 중복확인
    @Transactional(readOnly = true)
    public boolean isEmailAvailable(String email) {
        validateEmail(email);
        return !accountMapper.existsByEmail(email);
    }

    //닉네임(username) 중복 확인
    @Transactional(readOnly = true)
    public boolean isUsernameAvailable(String username) {
        validateUsername(username);
        return !accountMapper.existsByUsername(username);
    }

    // 로그인: DB 에 저장된 이메일인지와 확인, 비밀번호가 일치하는지 확인 후 access token 과 refresh token 생성 후 로그인
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

    // 로그아웃: refresh token 유효성 검사 및 login 한 사용자와 logout 시키려는 계정의 사용자 일치 여부 확인 후 로그아웃
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

    // 회원가입: 이메일/닉네임 형식 및 중복 확인 후 계정 저장
    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        validateEmail(request.getEmail());
        validateUsername(request.getUsername());

        if (accountMapper.existsByEmail(request.getEmail())) {
            throw new BusinessException(ErrorCode.AUTH_EMAIL_ALREADY_EXISTS);
        }
        if (accountMapper.existsByUsername(request.getUsername())) {
            throw new BusinessException(ErrorCode.AUTH_USERNAME_ALREADY_EXISTS);
        }

        createAccount(request);
        return new SignUpResponse(request.getEmail(), request.getUsername());
    }

    private static void validateEmail(String email) {
        if (email == null || email.isBlank() || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new BusinessException(ErrorCode.AUTH_INVALID_EMAIL_FORMAT);
        }
    }

    private static void validateUsername(String username) {
        if (username == null || username.isBlank() || !USERNAME_PATTERN.matcher(username).matches()) {
            throw new BusinessException(ErrorCode.AUTH_INVALID_USERNAME_FORMAT);
        }
    }

    private void createAccount(SignUpRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Account newAccount = new Account(request.getEmail(), request.getUsername(), encodedPassword);
        accountMapper.insertNewAccount(newAccount);
    }
}
