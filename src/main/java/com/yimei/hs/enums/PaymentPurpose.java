package com.yimei.hs.enums;

import java.util.ArrayList;
import java.util.List;

public enum PaymentPurpose {

    PAYMENT_FOR_GOODS("货款"),
    TRADE_DEFICIT("贸易逆差"),
    FIAL_PAYMENT("货款尾款"),
    DEPOSITECASH("保证金"),
    FREIGNHT("运费");

    public String value;

    PaymentPurpose(String value) {
        this.value = value;
    }

    public static List<EnumEntity> list() {
        List<EnumEntity> data = new ArrayList<>();
        for (PaymentPurpose bt : PaymentPurpose.values()) {
            data.add(new EnumEntity(bt.name(), bt.value));
        }
        return data;
    }

    public static String name = "PaymentPurpose";

}