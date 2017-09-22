package com.yimei.hs.enums;

/**
 *   客户类型: 上游, 贸易商, 下游, 资金方, 运输公司, 账务公司
 */
public enum CustomerType {


    UPSTREAM("上游戏"),
    DOWTSTREAM("下游"),
    TRAFFICKCER("贸易公司"),
    FUNDER("资金方"),
    TRANSPORT_COMPANY("运输方"),
    ACCOUNTING_COMPANY("账务公司");

    public String value;

    CustomerType(String value) {
        this.value = value;
    }
}
