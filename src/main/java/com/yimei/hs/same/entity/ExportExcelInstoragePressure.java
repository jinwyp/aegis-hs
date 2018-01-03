package com.yimei.hs.same.entity;

import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.enums.CargoType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportExcelInstoragePressure extends Order implements Serializable {

    private CargoType cargoType;
    // 事业部
    private String deptName;
    //  团队
    private String teamName;
    // 业务类型
    // 一级客户 上游Id |参与商Id
    private String upStreamPartyName;


    //   在途库存	 数量
    private BigDecimal totalInstorageTranitNum;
    // 在途库存 金额
    private BigDecimal totalInstorageTranitPrice;
    //    到港库存	数量
    private BigDecimal totalInstorageNum;
    //    到港库存 金额
    private BigDecimal totalInstoragePrice;


    //    合计  数量
    private BigDecimal totalNum;
    //    合计  金额
    private BigDecimal totalPrice;
    //    具体形成时间
    private String time;
    //    备注
    private String memo;
    // 业务类型
    private BusinessType businessType;
    // 账务公司名称
    private String AccoutCompanyName;

    private String arriveAddress;
}
