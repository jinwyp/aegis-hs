package com.yimei.hs.cang.mapper;

import com.yimei.hs.cang.entity.CangHuankuan;

public interface CangHuankuanMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CangHuankuan record);

    int insertSelective(CangHuankuan record);

    CangHuankuan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangHuankuan record);

    int updateByPrimaryKey(CangHuankuan record);
}