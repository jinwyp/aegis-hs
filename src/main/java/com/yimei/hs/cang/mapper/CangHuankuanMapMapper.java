package com.yimei.hs.cang.mapper;

import com.yimei.hs.cang.entity.CangHuankuanMap;

public interface CangHuankuanMapMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CangHuankuanMap record);

    int insertSelective(CangHuankuanMap record);

    CangHuankuanMap selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangHuankuanMap record);

    int updateByPrimaryKey(CangHuankuanMap record);
}