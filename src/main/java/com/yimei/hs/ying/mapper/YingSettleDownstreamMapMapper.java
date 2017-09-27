package com.yimei.hs.ying.mapper;

import com.yimei.hs.ying.entity.YingSettleDownstreamMap;

import java.util.List;

public interface YingSettleDownstreamMapMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingSettleDownstreamMap record);

    int deleteByOrderId(long id);

    int insertSelective(YingSettleDownstreamMap record);

    YingSettleDownstreamMap selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingSettleDownstreamMap record);

    int updateByPrimaryKey(YingSettleDownstreamMap record);

    List<YingSettleDownstreamMap> loadAll(Long orderId);
}