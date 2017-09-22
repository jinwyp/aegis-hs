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

    /**
     * 获取一页发票数据， 并取得这页数据对应的发票明细
     * @param pageYingInvoiceDTO
     * @return
     */
    Page<YingInvoice> getPage(PageYingInvoiceDTO pageYingInvoiceDTO);
}