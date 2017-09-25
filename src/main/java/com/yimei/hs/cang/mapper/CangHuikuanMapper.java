package com.yimei.hs.cang.mapper;

import com.yimei.hs.cang.entity.CangHuikuan;

public interface CangHuikuanMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CangHuikuan record);

    int insertSelective(CangHuikuan record);

    CangHuikuan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangHuikuan record);

    int updateByPrimaryKey(CangHuikuan record);
}