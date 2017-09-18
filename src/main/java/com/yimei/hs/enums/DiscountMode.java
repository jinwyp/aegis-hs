package com.yimei.hs.enums;

/**
 * 折扣类型
 */
public enum DiscountMode{

    RATE_DISCOUNT("利息折扣"),
    NO_DISCOUNT("无折扣"),
    CASH_DISCOUNT("现金折扣");


    public String value;
    DiscountMode(String value) {
        this.value = value;
    }
}