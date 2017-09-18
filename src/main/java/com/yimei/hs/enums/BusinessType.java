package com.yimei.hs.enums;

/**
 * 业务类型
 */
public enum BusinessType {


    YINGSHOU("应收帐款保理"),

    PURCHASE_ORDER_FINACING("订单融资"),

    CABIN_BISINESS("仓抵押业务");

    public String value;
    BusinessType(String value) {
        this.value = value;
    }
}