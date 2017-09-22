package com.yimei.hs.mapper;

import com.yimei.hs.entity.YingSettleTraffic;

public interface YingSettleTrafficMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingSettleTraffic record);

    int insertSelective(YingSettleTraffic record);

    YingSettleTraffic selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingSettleTraffic record);

    int updateByPrimaryKey(YingSettleTraffic record);
}