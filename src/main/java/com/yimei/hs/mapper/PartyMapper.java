package com.yimei.hs.mapper;

import com.yimei.hs.entity.Party;

public interface PartyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Party record);

    int insertSelective(Party record);

    Party selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Party record);

    int updateByPrimaryKey(Party record);
}