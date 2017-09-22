package com.yimei.hs.mapper;

import com.yimei.hs.entity.YingFee;

public interface YingFeeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingFee record);

    int insertSelective(YingFee record);

    YingFee selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingFee record);

    int updateByPrimaryKey(YingFee record);
}