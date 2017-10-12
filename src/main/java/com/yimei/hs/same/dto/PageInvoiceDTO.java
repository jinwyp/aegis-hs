package com.yimei.hs.same.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import com.yimei.hs.enums.InvoiceType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Created by hary on 2017/9/21.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)

public class PageInvoiceDTO extends BaseFilter<PageInvoiceDTO> {

    private Long orderId;

    private Long hsId;

    private InvoiceType invoiceType;

    private LocalDateTime openDate;

    private Long openCompanyId;

    private Long receiverId;





}