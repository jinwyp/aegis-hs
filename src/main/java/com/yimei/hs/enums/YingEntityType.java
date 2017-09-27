package com.yimei.hs.enums;

/**
 * Created by hary on 2017/9/26.
 */
public enum YingEntityType {

    order("应收业务线"),
    config("应收业务线-配置"),
    party("应收业务线-参与方"),
    fayun("应收业务线-发运"),
    huankuan("应收业务线-还款"),
    fukuan("应收业务线-付款"),
    huikuan("应收业务线-回款"),
    settleupstream("应收业务线-上游结算"),
    settledown("应收业务线-下游结算"),
    settletraffic("应收业务线-运输方结算"),
    fee("应收业务线-费用"),
    invoice("应收业务线-发票");

    private String value;
    YingEntityType(String value) {
        this.value = value;
    }
}
