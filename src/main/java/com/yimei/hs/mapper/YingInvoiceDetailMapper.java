package com.yimei.hs.mapper;

import com.yimei.hs.entity.YingInvoiceDetail;

public interface YingInvoiceDetailMapper {
    int deleteByPrimaryKey(Long id);

    long insert(YingInvoiceDetail record);

    int insertSelective(YingInvoiceDetail record);

    YingInvoiceDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingInvoiceDetail record);

    int updateByPrimaryKey(YingInvoiceDetail record);
}