package com.yimei.hs.cang.mapper;

import com.yimei.hs.cang.entity.CangSettleDownstream;

public interface CangSettleDownstreamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CangSettleDownstream record);

    int insertSelective(CangSettleDownstream record);

    CangSettleDownstream selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangSettleDownstream record);

    int updateByPrimaryKey(CangSettleDownstream record);
}