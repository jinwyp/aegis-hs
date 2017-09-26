package com.yimei.hs.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 费用科目
 */
public enum FeeClass {

    HELP_RECIVE_PAY_FEE("代收代垫运费"),
    TAX_MOTRO_FREIGHT("含税汽运运费"),
    TAX_SHIP_FREIGHT("含税水运运费"),
    TAX_RAIL_FREIGHT("含税火运运费"),
    SUPERVISE_FEE("监管费"),
    SERVICE_FEE("服务费"),
    BUSINESS_FEE("业务费");
    public String value;

    FeeClass(String value) {
        this.value = value;
    }

    public static List<EnumEntity> list() {
        List<EnumEntity> data = new ArrayList<>();
        for (FeeClass bt : FeeClass.values()) {
            data.add(new EnumEntity(bt.name(), bt.value));
        }
        return data;
    }

    public static String name = "FeeClass";

}
