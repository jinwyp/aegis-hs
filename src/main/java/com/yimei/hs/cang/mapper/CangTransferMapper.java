package com.yimei.hs.cang.mapper;

import com.yimei.hs.cang.entity.CangTransfer;

public interface CangTransferMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CangTransfer record);

    int insertSelective(CangTransfer record);

    CangTransfer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangTransfer record);

    int updateByPrimaryKey(CangTransfer record);
}