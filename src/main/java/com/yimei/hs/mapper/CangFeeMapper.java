package com.yimei.hs.mapper;

import com.yimei.hs.entity.CangFee;

public interface CangFeeMapper {
    int deleteByPrimaryKey(Long id);

    long insert(CangFee record);

    int insertSelective(CangFee record);

    CangFee selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangFee record);

    int updateByPrimaryKey(CangFee record);
}