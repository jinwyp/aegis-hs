package com.yimei.hs.enums;

/**
 *   货物到场状态
 */
public enum CargoArrivevalue {

    ARRIVE("已到场"),
    UNARRIVE("未到场");

    public String value;
    CargoArrivevalue(String value) {
        this.value = value;
    }
}