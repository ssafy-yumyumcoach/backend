package com.yumyumcoach.global.health;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DbHealthMapper {

    @Select("SELECT NOW()")
    String selectNow();
}

