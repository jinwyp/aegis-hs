package com.yimei.hs.cang.mapper;

import com.yimei.hs.cang.entity.CangOrder;

public interface CangOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CangOrder record);

    int insertSelective(CangOrder record);

    CangOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangOrder record);

    int updateByPrimaryKey(CangOrder record);
}