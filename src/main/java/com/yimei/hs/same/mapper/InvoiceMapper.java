package com.yimei.hs.same.mapper;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageInvoiceDTO;
import com.yimei.hs.same.entity.Invoice;
import org.apache.ibatis.annotations.Param;

public interface InvoiceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Invoice record);

    int insertSelective(Invoice record);

    /**
     * 取得一条发票记录， 以及发票记录的发票明细
     * @param id
     * @return
     */
    Invoice selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(Invoice record);

    int updateByPrimaryKey(Invoice record);

    /**
     * 获取一页发票数据， 并取得这页数据对应的发票明细
     * @param pageInvoiceDTO
     * @return
     */
    Page<Invoice> getPage(PageInvoiceDTO pageInvoiceDTO);

    int delete(long id);
}