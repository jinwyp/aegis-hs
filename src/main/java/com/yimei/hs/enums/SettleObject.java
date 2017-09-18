package com.yimei.hs.enums;

/**
 * 结算对象
 */
public enum SettleObject {


    UPSTREAM_SETTLE("上游结算"),

    DOWNSTREAM_SETTLE("下游结算"),

    TRAFFICT_SETTLE("运输方结算");


    public String value;

    SettleObject(String value) {
        this.value = value;
    }

}