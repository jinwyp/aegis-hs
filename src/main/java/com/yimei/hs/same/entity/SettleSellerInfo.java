package com.yimei.hs.same.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SettleSellerInfo {
    private Long orderId;

    private Long hsId;

    private boolean hasSettled;

    private LocalDateTime lastHuikuanDate;

    private BigDecimal purchaseCargoAmountOfMoney;// 结算金额

    private BigDecimal totalBuyerNums;//    结算数量

}
