package com.yimei.hs.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.entity.YingInvoice;
import com.yimei.hs.entity.dto.ying.PageYingInvoiceDTO;

public interface YingInvoiceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingInvoice record);

    int insertSelective(YingInvoice record);

    YingInvoice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YingInvoice record);

    int updateByPrimaryKey(YingInvoice record);

    Page<YingInvoice> getPage(PageYingInvoiceDTO pageYingInvoiceDTO);
}