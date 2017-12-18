package com.yimei.hs.same.entity;

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
public class ExportExcelDate implements Serializable{


    private LocalDateTime fukuanDate;

    private BigDecimal fukuanAmount;

    //excel 表格
    private BigDecimal amount;
    private LocalDateTime payDate;
    private LocalDateTime huikuanDate;
    private BigDecimal time;
    private BigDecimal rate;

    /**
     * 还款利息
     */
    private BigDecimal huankuanInterest;

    /**
     * 服务费
     */
    private BigDecimal huankuanServiceFee;

    /**
     * 客户还是ccs支付
     */
    private boolean isCcs;

    //回款

    /**
     * 回款日期
     */
    private LocalDateTime HuikuanTime;
    /**
     * 回款金额
     */
    private BigDecimal huikuanAmount;
    /**
     * 回款方式
     */
    private PayMode huikuanMode;
    /**
     * 贴现息
     */

    private BigDecimal tieRate;


}
