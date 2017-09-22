package com.yimei.hs.mapper;

import com.yimei.hs.entity.CangInvoiceDetail;

public interface CangInvoiceDetailMapper {
    int deleteByPrimaryKey(Long id);

    long insert(CangInvoiceDetail record);

    int insertSelective(CangInvoiceDetail record);

    CangInvoiceDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangInvoiceDetail record);

    int updateByPrimaryKey(CangInvoiceDetail record);
}