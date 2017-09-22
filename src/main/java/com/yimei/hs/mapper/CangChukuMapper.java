package com.yimei.hs.mapper;

import com.yimei.hs.entity.CangChuku;

public interface CangChukuMapper {
    int deleteByPrimaryKey(Long id);

    long insert(CangChuku record);

    int insertSelective(CangChuku record);

    CangChuku selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangChuku record);

    int updateByPrimaryKey(CangChuku record);
}