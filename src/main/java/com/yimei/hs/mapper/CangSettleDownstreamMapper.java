package com.yimei.hs.mapper;

import com.yimei.hs.entity.CangSettleDownstream;

public interface CangSettleDownstreamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CangSettleDownstream record);

    int insertSelective(CangSettleDownstream record);

    CangSettleDownstream selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangSettleDownstream record);

    int updateByPrimaryKey(CangSettleDownstream record);
}