package com.yimei.hs.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 折扣类型
 */
public enum DiscountMode{

    RATE_DISCOUNT("利息折扣"),
    NO_DISCOUNT("无折扣"),
    CASH_DISCOUNT("现金折扣");


    public String value;
    DiscountMode(String value) {
        this.value = value;
    }

    public static List<EnumEntity> list() {
        List<EnumEntity> data = new ArrayList<>();
        for (DiscountMode bt : DiscountMode.values()) {
            data.add(new EnumEntity(bt.name(), bt.value));
        }
        return data;
    }

    public static String name = "DiscountMode";


}