package com.yimei.hs.cang.mapper;

import com.yimei.hs.cang.entity.CangRuku;

public interface CangRukuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CangRuku record);

    int insertSelective(CangRuku record);

    CangRuku selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangRuku record);

    int updateByPrimaryKey(CangRuku record);
}