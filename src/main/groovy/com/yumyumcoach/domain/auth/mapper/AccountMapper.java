package com.yumyumcoach.domain.auth.mapper;

import com.yumyumcoach.domain.auth.entity.Account;
import org.apache.ibatis.annotations.Mapper;

/*
"/api/auth/..." 요청의 Mapper
 */

@Mapper
public interface AccountMapper {
    // 로그인 시 필요한 이메일로 계정 찾기
    Account findByEmail(String email);

    // 이메일 중복 확인
    boolean existsByEmail(String email);

    // 닉네임(username) 중복 확인
    boolean existsByUsername(String username);
}
