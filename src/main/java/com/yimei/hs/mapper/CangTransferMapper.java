package com.yimei.hs.mapper;

import com.yimei.hs.entity.CangTransfer;

public interface CangTransferMapper {
    int deleteByPrimaryKey(Long id);

    long insert(CangTransfer record);

    int insertSelective(CangTransfer record);

    CangTransfer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangTransfer record);

    int updateByPrimaryKey(CangTransfer record);
}