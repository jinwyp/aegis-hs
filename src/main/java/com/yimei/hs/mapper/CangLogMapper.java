package com.yimei.hs.mapper;

import com.yimei.hs.entity.CangLog;

public interface CangLogMapper {
    int deleteByPrimaryKey(Long id);

    long insert(CangLog record);

    int insertSelective(CangLog record);

    CangLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangLog record);

    int updateByPrimaryKey(CangLog record);
}