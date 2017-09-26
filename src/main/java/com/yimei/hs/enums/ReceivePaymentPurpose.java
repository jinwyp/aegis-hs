package com.yimei.hs.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 回款用途
 */
public enum ReceivePaymentPurpose {

    PAYMENT_FOR_GOODS("货款"),
    DEPOSITECASH("保证金");

    public String value;

    ReceivePaymentPurpose(String value) {
        this.value = value;
    }

    public static List<EnumEntity> list() {
        List<EnumEntity> data = new ArrayList<>();
        for (ReceivePaymentPurpose bt : ReceivePaymentPurpose.values()) {
            data.add(new EnumEntity(bt.name(), bt.value));
        }
        return data;
    }

    public static String name = "ReceivePaymentPurpose";
}