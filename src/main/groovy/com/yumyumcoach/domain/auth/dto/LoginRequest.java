package com.yumyumcoach.domain.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/*
로그인 요청 (email, password)
 */

@Getter
@NoArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
}
