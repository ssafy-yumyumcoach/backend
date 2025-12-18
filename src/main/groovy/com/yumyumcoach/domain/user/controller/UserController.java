package com.yumyumcoach.domain.user.controller;

import com.yumyumcoach.domain.user.dto.MyPageResponse;
import com.yumyumcoach.domain.user.dto.MyTitleResponse;
import com.yumyumcoach.domain.user.service.UserService;
import com.yumyumcoach.global.common.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/me")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 내 마이페이지 조회
     */
    @GetMapping("/mypage")
    public MyPageResponse getMyPage() {
        String email = CurrentUser.email();
        return userService.getMyPage(email);
    }

    /**
     * 내 대표뱃지 설정
     */
    @PutMapping("/titles/{titleId}")
    public MyTitleResponse selectMyTitle(@PathVariable Long titleId) {
        String email = CurrentUser.email();
        return userService.selectMyTitle(email, titleId);
    }
}

