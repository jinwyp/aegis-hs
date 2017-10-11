package com.yimei.hs.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务类型
 */
public enum InStorageStatus {


    IN_STORAGE("已入库"),

    IN_TRANIT("运输中");



    public String value;
    InStorageStatus(String value) {
        this.value = value;
    }

    public static List<EnumEntity> list() {
        List<EnumEntity> data = new ArrayList<>();
        for (InStorageStatus bt : InStorageStatus.values()) {
            data.add(new EnumEntity(bt.name(), bt.value));
        }
        return data;
    }

    public static String name = "InStorageStatus";
}