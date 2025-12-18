package com.yumyumcoach.domain.auth.mapper;

import com.yumyumcoach.domain.auth.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/*
login 로직에서 필요한 email 로 회원찾기

성공 시: 해당 Account 반환
실패 시: null 반환
 */

@Mapper
public interface AccountMapper {
    Account findByEmail(@Param("email") String email);
}
