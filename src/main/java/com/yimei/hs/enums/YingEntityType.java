package com.yimei.hs.enums;

/**
 * Created by hary on 2017/9/26.
 */
public enum YingEntityType {

    YING_ORDER("应收业务线"),
    YING_ORDER_CONFIG("应收业务线-配置"),
    YING_ORDER_PARTY("应收业务线-参与方"),
    YING_ORDER_FAYUN("应收业务线-发运"),
    YING_ORDER_HUANKUAN("应收业务线-还款"),
    YING_ORDER_FUKUAN("应收业务线-付款"),
    YING_ORDER_HUIKUAN("应收业务线-回款"),
    YING_ORDER_SETTLE_UP("应收业务线-上游结算"),
    YING_ORDER_SETTLE_DOWN("应收业务线-下游结算"),
    YING_ORDER_SETTLE_TRAFFIC("应收业务线-运输方结算"),
    YING_ORDER_FEE("应收业务线-费用"),
    YING_ORDER_INVOICE("应收业务线-发票");

    private String value;
    YingEntityType(String value) {
        this.value = value;
    }
}
