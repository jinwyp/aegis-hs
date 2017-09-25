package com.yimei.hs.cang.mapper;

import com.yimei.hs.cang.entity.CangInvoiceDetail;

public interface CangInvoiceDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CangInvoiceDetail record);

    int insertSelective(CangInvoiceDetail record);

    CangInvoiceDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangInvoiceDetail record);

    int updateByPrimaryKey(CangInvoiceDetail record);
}