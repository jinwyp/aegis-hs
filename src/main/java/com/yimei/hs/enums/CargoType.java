package com.yimei.hs.enums;

public enum CargoType {
    COAL("煤炭"),
    STEELS("钢材");
    private String value;

    CargoType(String value) {
        this.value = value;
    }
}