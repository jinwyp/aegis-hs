package com.yimei.hs.mapper;

import com.yimei.hs.entity.CangOrderConfig;

public interface CangOrderConfigMapper {
    int deleteByPrimaryKey(Long id);

    long insert(CangOrderConfig record);

    int insertSelective(CangOrderConfig record);

    CangOrderConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangOrderConfig record);

    int updateByPrimaryKey(CangOrderConfig record);
}