package com.yimei.hs.same.mapper;

import com.yimei.hs.same.entity.InvoiceDetail;

import java.util.List;

public interface InvoiceDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InvoiceDetail record);

    int delete(long id);

    int insertSelective(InvoiceDetail record);

    InvoiceDetail selectByPrimaryKey(Long id);

    InvoiceDetail selectByPrimaryKeyDeleted(Long id);

    int updateByPrimaryKeySelective(InvoiceDetail record);

    int updateByPrimaryKey(InvoiceDetail record);

    int deleteByInvoiceId(long invoiceId);

    List<InvoiceDetail> getList(Long invoiceId);
}