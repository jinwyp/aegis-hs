package com.yimei.hs.mapper;

import com.yimei.hs.entity.YingInvoice;

public interface YingInvoiceMapper {
    int deleteByPrimaryKey(Long id);

    long insert(YingInvoice record);

    int insertSelective(YingInvoice record);

    YingInvoice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingInvoice record);

    int updateByPrimaryKey(YingInvoice record);
}