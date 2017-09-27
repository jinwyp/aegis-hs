package com.yimei.hs.enums;

/**
 * Created by hary on 2017/9/26.
 */
public enum  CangEntityType {

    order("苍押业务线"),
    config("苍押业务线配置"),
    party("苍押业务线参与方"),
    ruku("苍押业务线入库"),
    chuku("苍押业务线出库"),
    fukuan("苍押业务线付款"),
    huikuan("苍押业务线回款"),
    settleup("苍押业务线上游结算"),
    settledown("苍押业务线下游结算"),
    settletraffic("苍押业务线运输方结算"),
    fee("苍押业务线费用"),
    invoice("苍押业务线发票");

    private String value;
    CangEntityType(String value) {
        this.value = value;
    }

}
