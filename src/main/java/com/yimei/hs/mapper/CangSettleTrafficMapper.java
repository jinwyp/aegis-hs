package com.yimei.hs.mapper;

import com.yimei.hs.entity.CangSettleTraffic;

public interface CangSettleTrafficMapper {
    int deleteByPrimaryKey(Long id);

    long insert(CangSettleTraffic record);

    int insertSelective(CangSettleTraffic record);

    CangSettleTraffic selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangSettleTraffic record);

    int updateByPrimaryKey(CangSettleTraffic record);
}