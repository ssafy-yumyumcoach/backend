package com.yumyumcoach.domain.diet.mapper;

import com.yumyumcoach.domain.diet.dto.CreateDietRecordRequest;
import com.yumyumcoach.domain.diet.dto.DietRecordDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DietRecordMapper {

    int insertDietRecord(CreateDietRecordRequest request);

    int updateDietRecord(@Param("id") Long id, CreateDietRecordRequest request);

    int deleteDietRecord(@Param("id") Long id, @Param("userId") String userId);

    DietRecordDto selectDietRecordDetail(@Param("id") Long id);

    List<DietRecordDto> selectDietRecordsByUser(
            @Param("userId") String userId,
            @Param("offset") int offset,
            @Param("limit") int limit
    );
}

