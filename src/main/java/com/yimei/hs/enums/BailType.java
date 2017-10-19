package com.yimei.hs.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 保证金类型
 */
public enum BailType {


    RECV_UP("收上游保证金"),

    BACK_UP("退上游保证金"),

    PAY_DOWN("付上游保证金"),

    BACK_DOWN("下游退保证金");


    public String value;
    BailType(String value) {
        this.value = value;
    }


    public static List<EnumEntity> list() {
        List<EnumEntity> data = new ArrayList<>();
        for (BailType bt : BailType.values()) {
            data.add(new EnumEntity(bt.name(), bt.value));
        }
        return data;
    }

    public static String name = "BailType";


}