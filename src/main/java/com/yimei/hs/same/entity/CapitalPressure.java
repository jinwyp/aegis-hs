package com.yimei.hs.same.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CapitalPressure {
    private Long hsId;

    private Long orderId;

    private Long receiveCompanyId;

    /**
     * 占压金额
     */
    private BigDecimal unInvoicePrice;

    /**
     * 未开票金额
     */
    private BigDecimal partiesCapitalPressure;

    private Long id;
}
