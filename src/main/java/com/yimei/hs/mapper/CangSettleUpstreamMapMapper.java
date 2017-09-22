package com.yimei.hs.mapper;

import com.yimei.hs.entity.CangSettleUpstreamMap;

public interface CangSettleUpstreamMapMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CangSettleUpstreamMap record);

    int insertSelective(CangSettleUpstreamMap record);

    CangSettleUpstreamMap selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangSettleUpstreamMap record);

    int updateByPrimaryKey(CangSettleUpstreamMap record);
}