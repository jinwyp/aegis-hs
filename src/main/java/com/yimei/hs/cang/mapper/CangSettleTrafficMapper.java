package com.yimei.hs.cang.mapper;

import com.yimei.hs.cang.entity.CangSettleTraffic;

public interface CangSettleTrafficMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CangSettleTraffic record);

    int insertSelective(CangSettleTraffic record);

    CangSettleTraffic selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangSettleTraffic record);

    int updateByPrimaryKey(CangSettleTraffic record);
}