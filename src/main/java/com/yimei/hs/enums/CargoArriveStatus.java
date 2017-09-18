package com.yimei.hs.enums;

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
}