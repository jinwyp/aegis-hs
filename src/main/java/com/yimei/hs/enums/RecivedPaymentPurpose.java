package com.yimei.hs.enums;

/**
 * 回款用途
 */
public enum RecivedPaymentPurpose {

    PAYMENT_FOR_GOODS("货款"),
    DEPOSITECASH("保证金");

    public String status;

    RecivedPaymentPurpose(String status) {
        this.status = status;
    }

}