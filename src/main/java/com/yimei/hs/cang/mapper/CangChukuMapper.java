package com.yimei.hs.cang.mapper;

import com.yimei.hs.cang.entity.CangChuku;

public interface CangChukuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CangChuku record);

    int insertSelective(CangChuku record);

    CangChuku selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangChuku record);

    int updateByPrimaryKey(CangChuku record);
}