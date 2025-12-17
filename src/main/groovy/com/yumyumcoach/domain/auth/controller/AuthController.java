package com.yumyumcoach.domain.auth.controller;

import com.yumyumcoach.domain.auth.dto.*;
import com.yumyumcoach.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-in")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/sign-out")
    public ResponseEntity<LogoutResponse> logout(@AuthenticationPrincipal String email,
                                                 @RequestBody LogoutRequest request) {
        authService.logout(email, request.getRefreshToken());
        return ResponseEntity.ok(new LogoutResponse("로그아웃 되었습니다."));
    }

    @GetMapping("/check-email")
    public ResponseEntity<EmailCheckResponse> checkEmail(@RequestParam("email") String email) {
        boolean available = authService.isEmailAvailable(email);
        return ResponseEntity.ok(new EmailCheckResponse(email, available));
    }
}
