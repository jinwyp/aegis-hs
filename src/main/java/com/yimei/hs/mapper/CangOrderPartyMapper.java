package com.yimei.hs.mapper;

import com.yimei.hs.entity.CangOrderParty;

public interface CangOrderPartyMapper {
    int deleteByPrimaryKey(Long id);

    long insert(CangOrderParty record);

    int insertSelective(CangOrderParty record);

    CangOrderParty selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangOrderParty record);

    int updateByPrimaryKey(CangOrderParty record);
}