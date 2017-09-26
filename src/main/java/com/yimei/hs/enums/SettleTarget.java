package com.yimei.hs.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 结算对象
 */
public enum SettleTarget {


    UPSTREAM_SETTLE("上游结算"),

    DOWNSTREAM_SETTLE("下游结算"),

    TRAFFICT_SETTLE("运输方结算");


    public String value;

    SettleTarget(String value) {
        this.value = value;
    }

    public static List<EnumEntity> list() {
        List<EnumEntity> data = new ArrayList<>();
        for (SettleTarget bt : SettleTarget.values()) {
            data.add(new EnumEntity(bt.name(), bt.value));
        }
        return data;
    }

    public static String name = "SettleTarget";

}