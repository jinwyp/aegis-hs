package com.yimei.hs.enums;

/**
 * Created by hary on 2017/9/26.
 */
public enum  CangEntityType {

    CANG_ORDER("苍押业务线"),
    CANG_ORDER_CONFIG("苍押业务线配置"),
    CANG_ORDER_PARTY("苍押业务线参与方"),
    CANG_ORDER_RUKU("苍押业务线入库"),
    CANG_ORDER_CHUKU("苍押业务线出库"),
    CANG_ORDER_FUKUAN("苍押业务线付款"),
    CANG_ORDER_HUIKUAN("苍押业务线回款"),
    CANG_ORDER_SETTLE_UP("苍押业务线上游结算"),
    CANG_ORDER_SETTLE_DOWN("苍押业务线下游结算"),
    CANG_ORDER_SETTLE_TRAFFIC("苍押业务线运输方结算"),
    CANG_ORDER_FEE("苍押业务线费用"),
    CANG_ORDER_INVOICE("苍押业务线发票");

    private String value;
    CangEntityType(String value) {
        this.value = value;
    }

}
