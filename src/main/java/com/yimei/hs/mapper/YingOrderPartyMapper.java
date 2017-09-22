package com.yimei.hs.mapper;

import com.yimei.hs.entity.YingOrderParty;

public interface YingOrderPartyMapper {
    int deleteByPrimaryKey(Long id);

    long insert(YingOrderParty record);

    int insertSelective(YingOrderParty record);

    YingOrderParty selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingOrderParty record);

    int updateByPrimaryKey(YingOrderParty record);
}