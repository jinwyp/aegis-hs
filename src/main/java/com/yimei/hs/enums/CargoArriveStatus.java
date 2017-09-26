package com.yimei.hs.enums;

import java.util.ArrayList;
import java.util.List;

/**
 *   货物到场状态
 */
public enum CargoArriveStatus {

    ARRIVE("已到场"),
    UNARRIVE("未到场");

    public String value;
    CargoArriveStatus(String value) {
        this.value = value;
    }

    public static List<EnumEntity> list() {
        List<EnumEntity> data = new ArrayList<>();
        for (CargoArriveStatus bt : CargoArriveStatus.values()) {
            data.add(new EnumEntity(bt.name(), bt.value));
        }
        return data;
    }

    public static String name = "CargoArriveStatus";
}