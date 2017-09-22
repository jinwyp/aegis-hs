package com.yimei.hs.mapper;

import com.yimei.hs.entity.CangSettleUpstream;

public interface CangSettleUpstreamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CangSettleUpstream record);

    int insertSelective(CangSettleUpstream record);

    CangSettleUpstream selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangSettleUpstream record);

    int updateByPrimaryKey(CangSettleUpstream record);
}