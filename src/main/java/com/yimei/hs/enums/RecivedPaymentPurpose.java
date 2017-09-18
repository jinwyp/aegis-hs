package com.yimei.hs.enums;

/**
 * 回款用途
 */
public enum RecivedPaymentPurpose {

    PAYMENT_FOR_GOODS("货款"),
    DEPOSITECASH("保证金");

    public String value;

    RecivedPaymentPurpose(String value) {
        this.value = value;
    }

}