package com.yimei.hs.mapper;

import com.yimei.hs.entity.CangInvoice;

public interface CangInvoiceMapper {
    int deleteByPrimaryKey(Long id);

    long insert(CangInvoice record);

    int insertSelective(CangInvoice record);

    CangInvoice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangInvoice record);

    int updateByPrimaryKey(CangInvoice record);
}