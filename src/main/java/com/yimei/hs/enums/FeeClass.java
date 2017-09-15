package com.yimei.hs.enums;

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
    public String status;

    FeeClass(String status) {
        this.status = status;
    }
}
