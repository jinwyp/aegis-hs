package com.yimei.hs.enums;

public enum CargoType {
    COAL("煤炭"),
    STEELS("钢材");
    private String status;
    CargoType(String status) {
        this.status = status;
    }
}