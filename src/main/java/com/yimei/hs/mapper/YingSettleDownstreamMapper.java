package com.yimei.hs.mapper;

import com.yimei.hs.entity.YingSettleDownstream;

public interface YingSettleDownstreamMapper {
    int deleteByPrimaryKey(Long id);

    long insert(YingSettleDownstream record);

    int insertSelective(YingSettleDownstream record);

    YingSettleDownstream selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingSettleDownstream record);

    int updateByPrimaryKey(YingSettleDownstream record);
}