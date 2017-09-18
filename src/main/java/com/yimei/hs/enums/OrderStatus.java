package com.yimei.hs.enums;

public enum OrderStatus {
    COMPLETED("完结"),
    UNCOMPLETED("未完结");
    private String value;

    OrderStatus(String value) {
        this.value = value;
    }
}