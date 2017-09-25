package com.yimei.hs.ying.entity;

import com.yimei.hs.enums.InvoiceDirection;
import com.yimei.hs.enums.InvoiceType;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class YingInvoice implements Serializable {
    private Long id;

    private Long orderId;

    private Long hsId;

    private InvoiceDirection invoiceDirection;

    private InvoiceType invoiceType;

    private LocalDateTime openDate;

    private Long openCompanyId;

    private Long recieverId;

    private LocalDateTime tsc;

    List<YingInvoiceDetail> details;

}
