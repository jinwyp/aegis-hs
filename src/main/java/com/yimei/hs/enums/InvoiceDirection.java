package com.yimei.hs.enums;
/**
 *  发票属性（发票类型）
 */
public enum InvoiceDirection{

    INCOME("进项发票"),
    OUTOUT("销项发票");
    public String status;
    InvoiceDirection(String status) {
        this.status = status;
    }

}