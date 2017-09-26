package com.yimei.hs.enums;

import java.util.ArrayList;
import java.util.List;

public enum OrderStatus {
    COMPLETED("完结"),
    UNCOMPLETED("未完结");
    private String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public static List<EnumEntity> list() {
        List<EnumEntity> data = new ArrayList<>();
        for (OrderStatus bt : OrderStatus.values()) {
            data.add(new EnumEntity(bt.name(), bt.value));
        }
        return data;
    }
    public static String name = "OrderStatus";
}