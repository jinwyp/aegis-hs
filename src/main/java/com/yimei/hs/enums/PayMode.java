package com.yimei.hs.enums;

/**
 * 付款方式
 */
public enum PayMode {

    ELEC_REMITTANCE("电子汇款"),
    BANK_ACCEPTANCE("银行承兑"),
    BUSINESS_ACCEPTANCE("商业承兑"),
    CASH("现金");

    public String value;

    PayMode(String value) {
        this.value = value;
    }
}
