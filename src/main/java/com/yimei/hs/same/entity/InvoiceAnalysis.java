package com.yimei.hs.same.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceAnalysis {
    private Long hsId;
    private Long orderId;
    private Long receiverId;
    private BigDecimal totalPriceTax;
    private BigDecimal totalAmount;
}
