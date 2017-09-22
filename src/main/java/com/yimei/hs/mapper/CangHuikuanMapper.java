package com.yimei.hs.mapper;

import com.yimei.hs.entity.CangHuikuan;

public interface CangHuikuanMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CangHuikuan record);

    int insertSelective(CangHuikuan record);

    CangHuikuan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangHuikuan record);

    int updateByPrimaryKey(CangHuikuan record);
}