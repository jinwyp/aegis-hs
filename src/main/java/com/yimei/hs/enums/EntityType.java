package com.yimei.hs.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hary on 2017/9/26.
 */
public enum EntityType {

    yingOrderInsert("应收业务线新增"),
    yingOrderUpdate("应收业务线修改"),
    yingOrderDel("应收业务线删除"),

    yingConfigInsert("应收业务线-配置新增"),
    yingConfigUpdate("应收业务线-配置修改"),
    yingConfigDel("应收业务线-配置删除"),

    yingPartyInsert("应收业务线-参与方新增"),
    yingPartyUpdate("应收业务线-参与方修改"),
    yingPartyDel("应收业务线-参与方删除"),

    yingFayunInsert("应收业务线-发运新增"),
    yingFayunUpdate("应收业务线-发运修改"),
    yingFayunDel("应收业务线-发运删除"),


    yingHuankuanInsert("应收业务线-还款新增"),
    yingHuankuanUpdate("应收业务线-还款修改"),
    yingHuankuanDel("应收业务线-还款删除"),

    yingHukuanInsert("应收业务线-付款新增"),
    yingHukuanUpdate("应收业务线-付款删除"),
    yingHukuanDel("应收业务线-付款修改"),

    yingHuikuanInsert("应收业务线-回款新增"),
    yingHuikuanUpdate("应收业务线-回款修改"),
    yingHuikuanDel("应收业务线-回款删除"),

    yingSettleUpstreamInsert("应收业务线-上游结算添加"),
    yingSettleUpstreamUpdate("应收业务线-上游结算更新"),
    yingSettleUpstreamDel("应收业务线-上游结算删除"),


    yingSettleDownInsert("应收业务线-下游结算添加"),
    yingSettleDownUpdate("应收业务线-下游结算更新"),
    yingSettleDownDel("应收业务线-下游结算删除"),

    yingSettleTrafficInsert("应收业务线-运输方结算添加"),
    yingSettleTrafficUpdate("应收业务线-运输方结算更新"),
    yingSettleTrafficDel("应收业务线-运输方结算删除"),


    yingFeeInsert("应收业务线-费用新增"),
    yingFeeUpdate("应收业务线-费用修改"),
    yingFeeDel("应收业务线-费用删除"),

    yingJiekuanInsert("应收业务线-借款新增"),
    yingJiekuanUpdate("应收业务线-借款修改"),
    yingJiekuanDel("应收业务线-借款删除"),

    yingFukuanInsert("应收业务线-付新增"),
    yingFukuanUpdate("应收业务线-付款修改"),
    yingFukuanDel("应收业务线-付款删除"),

    yingBailInsert("应收业务线-保证金新增"),
    yingBailUpdate("应收业务线-保证金修改"),
    yingBailDel("应收业务线-保证金删除"),

    yingInvoiceInsert("应收业务线-发票新增"),
    yingInvoiceUpdate("应收业务线-发票更新"),
    yingInvoiceDel("应收业务线-发票删除"),


    cangOrderInsert("仓押业务线新增"),
    cangOrderUpdate("仓押业务线修改"),
    cangOrderDel("仓押业务线删除"),

    cangConfigInsert("仓押业务线-配置新增"),
    cangConfigUpdate("仓押业务线-配置修改"),
    cangConfigDel("仓押业务线-配置删除"),

    cangPartyInsert("仓押业务线-参与方新增"),
    cangPartyUpdate("仓押业务线-参与方修改"),
    cangPartyDel("仓押业务线-参与方删除"),

    cangFayunInsert("仓押业务线-发运新增"),
    cangFayunUpdate("仓押业务线-发运修改"),
    cangFayunDel("仓押业务线-发运删除"),

    cangHuankuanInsert("仓押业务线-还款新增"),
    cangHuankuanUpdate("仓押业务线-还款修改"),
    cangHuankuanDel("仓押业务线-还款删除"),

    cangHukuanInsert("仓押业务线-付款新增"),
    cangHukuanUpdate("仓押业务线-付款删除"),
    cangHukuanDel("仓押业务线-付款修改"),

    cangHuikuanInsert("仓押业务线-回款新增"),
    cangHuikuanUpdate("仓押业务线-回款修改"),
    cangHuikuanDel("仓押业务线-回款删除"),

    cangSettleUpstreamInsert("仓押业务线-上游结算添加"),
    cangSettleUpstreamUpdate("仓押业务线-上游结算更新"),
    cangSettleUpstreamDel("仓押业务线-上游结算删除"),


    cangSettleDownInsert("仓押业务线-下游结算添加"),
    cangSettleDownUpdate("仓押业务线-下游结算更新"),
    cangSettleDownDel("仓押业务线-下游结算删除"),

    cangSettleTrafficInsert("仓押业务线-运输方结算添加"),
    cangSettleTrafficUpdate("仓押业务线-运输方结算更新"),
    cangSettleTrafficDel("仓押业务线-运输方结算删除"),


    cangFeeInsert("仓押业务线-费用新增"),
    cangFeeUpdate("仓押业务线-费用修改"),
    cangFeeDel("仓押业务线-费用删除"),

    cangBailInsert("仓押业务线-保证金新增"),
    cangBailUpdate("仓押业务线-保证金修改"),
    cangBailDel("仓押业务线-保证金删除"),

    cangInvoiceInsert("仓押业务线-发票新增"),
    cangInvoiceUpdate("仓押业务线-发票更新"),
    cangInvoiceDel("仓押业务线-发票删除"),

    cangRuKuInsert("仓押业务线-入库新增"),
    cangRuKuUpdate("仓押业务线-入库修改"),
    cangRuKuDel("仓押业务线-入库删除"),

    cangJiekuanInsert("仓押业务线-借款新增"),
    cangJiekuanUpdate("仓押业务线-借款修改"),
    cangJiekuanDel("仓押业务线-借款删除"),

    cangFukuanInsert("仓押业务线-付款新增"),
    cangFukuanUpdate("仓押业务线-付款修改"),
    cangFukuanDel("仓押业务线-付款删除"),

    cangChuKuInsert("仓押业务线-出库新增"),
    cangChuKuUpdate("仓押业务线-出库修改"),
    cangChuKuDel("仓押业务线-出库删除");

    private String value;

    EntityType(String value) {
        this.value = value;
    }


    public static List<EnumEntity> list() {
        List<EnumEntity> data = new ArrayList<>();
        for (EntityType bt : EntityType.values()) {
            data.add(new EnumEntity(bt.name(), bt.value));
        }
        return data;
    }

    public static String name = "EntityType";

}
