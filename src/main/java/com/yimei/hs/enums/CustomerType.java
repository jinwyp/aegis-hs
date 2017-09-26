package com.yimei.hs.enums;

import java.util.ArrayList;
import java.util.List;

/**
 *   客户类型: 上游, 贸易商, 下游, 资金方, 运输公司, 账务公司
 */
public enum CustomerType {


    UPSTREAM("上游"),
    DOWNSTREAM("下游"),
    TRAFFICKCER("贸易公司"),
    FUNDER("资金方"),
    TRANSPORT_COMPANY("运输方"),
    ACCOUNTING_COMPANY("账务公司");

    public String value;

    CustomerType(String value) {
        this.value = value;
    }

    public static List<EnumEntity> list() {
        List<EnumEntity> data = new ArrayList<>();
        for (CustomerType bt : CustomerType.values()) {
            data.add(new EnumEntity(bt.name(), bt.value));
        }
        return data;
    }

    public static String name = "CustomerType";
}
