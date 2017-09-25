package com.yimei.hs.cang.mapper;

import com.yimei.hs.cang.entity.CangLog;

public interface CangLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CangLog record);

    int insertSelective(CangLog record);

    CangLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangLog record);

    int updateByPrimaryKey(CangLog record);
}