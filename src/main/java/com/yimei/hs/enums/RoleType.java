package com.yimei.hs.enums;

import java.util.ArrayList;
import java.util.List;

public enum RoleType {

    SUPRER_ADMIN("超级管理员"),
    DEPT_ADMIN("部门管理员"),
    ACSH_AT("核算会计");


    public String value;

    RoleType(String value) {
        this.value = value;
    }

    public static List<EnumEntity> list() {
        List<EnumEntity> data = new ArrayList<>();
        for (RoleType bt : RoleType.values()) {
            data.add(new EnumEntity(bt.name(), bt.value));
        }
        return data;
    }

    public static String name = "RoleType";
}
