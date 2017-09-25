package com.yimei.hs.cang.mapper;

import com.yimei.hs.cang.entity.CangOrderConfig;

public interface CangOrderConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CangOrderConfig record);

    int insertSelective(CangOrderConfig record);

    CangOrderConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangOrderConfig record);

    int updateByPrimaryKey(CangOrderConfig record);
}