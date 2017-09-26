package com.yimei.hs.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 结算方法
 */
public enum SettleMode {
    /*一票结算*/
    ONE_PAPER_SETTLE("一票结算"),
    TWO_PAPER_SETTLE("两票结算");

    public String value;

    SettleMode(String value) {
        this.value = value;
    }

    public static List<EnumEntity> list() {
        List<EnumEntity> data = new ArrayList<>();
        for (SettleMode bt : SettleMode.values()) {
            data.add(new EnumEntity(bt.name(), bt.value));
        }
        return data;
    }

    public static String name = "SettleMode";

}
