package com.yimei.hs.cang.mapper;

import com.yimei.hs.cang.entity.CangOrderParty;

public interface CangOrderPartyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CangOrderParty record);

    int insertSelective(CangOrderParty record);

    CangOrderParty selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangOrderParty record);

    int updateByPrimaryKey(CangOrderParty record);
}