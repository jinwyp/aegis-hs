package com.yimei.hs.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 运输方式
 */
public enum TrafficMode {


    MOTOR("汽运"),

    SHIP("船运"),

    RAIL("火运");


    public String value;
    TrafficMode(String value) {
        this.value = value;
    }


    public static List<EnumEntity> list() {
        List<EnumEntity> data = new ArrayList<>();
        for (TrafficMode bt : TrafficMode.values()) {
            data.add(new EnumEntity(bt.name(), bt.value));
        }
        return data;
    }

    public static String name = "TrafficMode";


}