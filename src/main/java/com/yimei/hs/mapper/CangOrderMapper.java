package com.yimei.hs.mapper;

import com.yimei.hs.entity.CangOrder;

public interface CangOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CangOrder record);

    int insertSelective(CangOrder record);

    CangOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangOrder record);

    int updateByPrimaryKey(CangOrder record);
}