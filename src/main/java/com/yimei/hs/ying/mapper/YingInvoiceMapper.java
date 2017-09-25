package com.yimei.hs.ying.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.YingInvoice;
import com.yimei.hs.ying.dto.PageYingInvoiceDTO;
import org.apache.ibatis.annotations.Param;

public interface YingInvoiceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YingInvoice record);

    int insertSelective(YingInvoice record);

    /**
     * 取得一条发票记录， 以及发票记录的发票明细
     * @param id
     * @return
     */
    YingInvoice selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(YingInvoice record);

    int updateByPrimaryKey(YingInvoice record);

    /**
     * 获取一页发票数据， 并取得这页数据对应的发票明细
     * @param pageYingInvoiceDTO
     * @return
     */
    Page<YingInvoice> getPage(PageYingInvoiceDTO pageYingInvoiceDTO);
}