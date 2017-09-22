package com.yimei.hs.enums;

/**
 * Created by xiangyang on 2016/11/22.
 */
public enum UserRole {

    /*系统管理员*/
    ADMIN("管理人员"),

    /*业务员*/
    CHARGER("主管"),


    FINANCIAL_ACCOUNTER("财务核算人员");

    public String value;

    UserRole(String value) {
        this.value = value;
    }
}
