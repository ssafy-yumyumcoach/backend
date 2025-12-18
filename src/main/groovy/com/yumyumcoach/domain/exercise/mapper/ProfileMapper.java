package com.yumyumcoach.domain.exercise.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProfileMapper {
    Double findCurrentWeightByEmail(@Param("email") String email);
}
