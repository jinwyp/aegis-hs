package com.yimei.hs.cang.mapper;

import com.yimei.hs.cang.entity.CangHuikuanMap;

public interface CangHuikuanMapMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CangHuikuanMap record);

    int insertSelective(CangHuikuanMap record);

    CangHuikuanMap selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangHuikuanMap record);

    int updateByPrimaryKey(CangHuikuanMap record);
}