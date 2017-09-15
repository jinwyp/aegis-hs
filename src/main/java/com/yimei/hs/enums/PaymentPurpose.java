package com.yimei.hs.enums;

public enum PaymentPurpose {

    PAYMENT_FOR_GOODS("货款"),
    TRADE_DEFICIT("贸易逆差"),
    FIAL_PAYMENT("货款尾款"),
    DEPOSITECASH("保证金"),
    FREIGNHT("运费");

    public String status;

    PaymentPurpose(String status) {
        this.status = status;
    }

}