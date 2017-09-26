package com.yimei.hs.enums;

import java.util.ArrayList;
import java.util.List;

public enum CargoType {
    COAL("煤炭"),
    STEELS("钢材");
    private String value;

    CargoType(String value) {
        this.value = value;
    }

    public static List<EnumEntity> list() {
        List<EnumEntity> data = new ArrayList<>();
        for (CargoType bt : CargoType.values()) {
            data.add(new EnumEntity(bt.name(), bt.value));
        }
        return data;
    }

    public static String name = "CargoType";

}