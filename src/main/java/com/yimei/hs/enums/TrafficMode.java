package com.yimei.hs.enums;

/**
 * 运输方式
 */
public enum TrafficMode {


    MOTOR("汽运"),

    SHIP("船运"),

    RAIL("火运");


    public String status;
    TrafficMode(String status) {
        this.status = status;
    }

}