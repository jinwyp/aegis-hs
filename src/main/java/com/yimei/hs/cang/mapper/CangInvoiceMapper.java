package com.yimei.hs.cang.mapper;

import com.yimei.hs.cang.entity.CangInvoice;

public interface CangInvoiceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CangInvoice record);

    int insertSelective(CangInvoice record);

    CangInvoice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CangInvoice record);

    int updateByPrimaryKey(CangInvoice record);
}