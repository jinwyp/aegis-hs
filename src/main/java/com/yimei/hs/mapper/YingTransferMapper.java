package com.yimei.hs.mapper;

import com.yimei.hs.entity.YingTransfer;

public interface YingTransferMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingTransfer record);

    int insertSelective(YingTransfer record);

    YingTransfer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingTransfer record);

    int updateByPrimaryKey(YingTransfer record);
}