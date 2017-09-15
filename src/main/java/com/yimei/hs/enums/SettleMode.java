package com.yimei.hs.enums;

/**
 * 结算方法
 */
public  enum SettleMode {
    /*一票结算*/
    ONE_PAPER_SETTLE("一票结算"),
    TWO_PAPER_SETTLE("两票结算");

    public String status;
    SettleMode(String status) {
        this.status = status;
    }
}
