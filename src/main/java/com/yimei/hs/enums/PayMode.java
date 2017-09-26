package com.yimei.hs.enums;

import java.util.ArrayList;
import java.util.List;

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

    public static List<EnumEntity> list() {
        List<EnumEntity> data = new ArrayList<>();
        for (PayMode bt : PayMode.values()) {
            data.add(new EnumEntity(bt.name(), bt.value));
        }
        return data;
    }

    public static String name = "PayMode";


}
