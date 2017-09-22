package com.yimei.hs.mapper;

import com.yimei.hs.entity.CangHuankuan;

public interface CangHuankuanMapper {
    int deleteByPrimaryKey(Long id);

    long insert(CangHuankuan record);

    int insertSelective(CangHuankuan record);

    CangHuankuan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangHuankuan record);

    int updateByPrimaryKey(CangHuankuan record);
}