package com.yimei.hs.mapper;

import com.yimei.hs.entity.YingSettleUpstream;

import java.util.List;

public interface YingSettleUpstreamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingSettleUpstream record);

    int insertSelective(YingSettleUpstream record);

    YingSettleUpstream selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingSettleUpstream record);

    int updateByPrimaryKey(YingSettleUpstream record);

    List<YingSettleUpstream> selectByOrderId(Long orderId);
}