package com.yimei.hs.mapper;

import com.yimei.hs.entity.CangHuikuanMap;

public interface CangHuikuanMapMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CangHuikuanMap record);

    int insertSelective(CangHuikuanMap record);

    CangHuikuanMap selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangHuikuanMap record);

    int updateByPrimaryKey(CangHuikuanMap record);
}