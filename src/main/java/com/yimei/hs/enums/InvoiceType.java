package com.yimei.hs.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 发票用途(发票项目)
 */
public enum InvoiceType {

    GOODS_INVOICE("货款发票"),
    FRIGHT_INVOICE("运费发票");

    public String value;

    InvoiceType(String value) {
        this.value = value;
    }

    public static List<EnumEntity> list() {
        List<EnumEntity> data = new ArrayList<>();
        for (InvoiceType bt : InvoiceType.values()) {
            data.add(new EnumEntity(bt.name(), bt.value));
        }
        return data;
    }

    public static String name = "InvoiceType";
}