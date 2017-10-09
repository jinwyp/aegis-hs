package com.yimei.hs.ying.mapper;

import com.yimei.hs.ying.entity.YingInvoiceDetail;

import java.util.List;

public interface YingInvoiceDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingInvoiceDetail record);

    int delete(long id);

    int insertSelective(YingInvoiceDetail record);

    YingInvoiceDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingInvoiceDetail record);

    int updateByPrimaryKey(YingInvoiceDetail record);

    int deleteByInvoiceId(long invoiceId);

    List<YingInvoiceDetail> getList(Long invoiceId);
}