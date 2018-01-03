package com.yimei.hs.same.entity;

import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.enums.PayMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportExcelUpstreamPressure extends Order implements Serializable {


    // 事业部
    private String deptName;
    //  团队
    private String teamName;
    // 业务类型
    // 一级客户 上游Id |参与商Id
    private String upStreamPartyName;
    //    二级客户  上游与主账户公司之间-其他参与方
    private String partName;
    //船名
    private String shipName;
    //    付款方式
    private String payMode;
    //    资金占压总额
    private BigDecimal pressureAmountOfPrice;
    //    银行贷款额（外部融资额）
    private BigDecimal bankDebtPrice;

    //    自有资金占压
    private BigDecimal ownerCapitalPressure;
    //    未开票金额（暂估）
    private BigDecimal unInvoicePrice;
    //    备注
    private String memo1;
    //    备注
    private String memo2;
    // 业务类型
    private BusinessType businessType;
    // 账务公司名称
    private String AccoutCompanyName;
    //终端客户名称
    private String terminalClientName;
}
