package com.yimei.hs.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务类型
 */
public enum BusinessType {


    YINGSHOU("应收帐款保理"),

    PURCHASE_ORDER_FINACING("订单融资"),

    CABIN_BISINESS("仓抵押业务");

    public String value;
    BusinessType(String value) {
        this.value = value;
    }

    public static List<EnumEntity> list() {
        List<EnumEntity> data = new ArrayList<>();
        for (BusinessType bt : BusinessType.values()) {
            data.add(new EnumEntity(bt.name(), bt.value));
        }
        return data;
    }

    public static String name = "BusinessType";
}