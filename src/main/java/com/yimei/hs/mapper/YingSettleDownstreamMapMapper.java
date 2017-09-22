package com.yimei.hs.mapper;

import com.yimei.hs.entity.YingSettleDownstreamMap;

public interface YingSettleDownstreamMapMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingSettleDownstreamMap record);

    int insertSelective(YingSettleDownstreamMap record);

    YingSettleDownstreamMap selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingSettleDownstreamMap record);

    int updateByPrimaryKey(YingSettleDownstreamMap record);
}