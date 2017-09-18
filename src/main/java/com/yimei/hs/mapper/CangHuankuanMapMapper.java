package com.yimei.hs.mapper;

import com.yimei.hs.entity.CangHuankuanMap;

public interface CangHuankuanMapMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CangHuankuanMap record);

    int insertSelective(CangHuankuanMap record);

    CangHuankuanMap selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangHuankuanMap record);

    int updateByPrimaryKey(CangHuankuanMap record);
}