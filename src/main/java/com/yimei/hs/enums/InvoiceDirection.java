package com.yimei.hs.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 发票属性（发票类型）
 */
public enum InvoiceDirection {

    INCOME("进项发票"),
    OUTOUT("销项发票");
    public String value;

    InvoiceDirection(String value) {
        this.value = value;
    }

    public static List<EnumEntity> list() {
        List<EnumEntity> data = new ArrayList<>();
        for (InvoiceDirection bt : InvoiceDirection.values()) {
            data.add(new EnumEntity(bt.name(), bt.value));
        }
        return data;
    }

    public static String name = "InvoiceDirection";

}