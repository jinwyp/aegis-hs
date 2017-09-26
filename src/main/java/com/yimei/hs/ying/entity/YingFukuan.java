package com.yimei.hs.ying.entity;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.enums.PayMode;
import com.yimei.hs.enums.PaymentPurpose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.security.DenyAll;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YingFukuan implements Serializable {

    @NotNull(groups = {UpdateGroup.class}, message = "id不能为空")
    private Long id;

    private Long orderId;

    @NotNull(groups = {CreateGroup.class}, message = "核算月不能为空")
    private Long hsId;

    private LocalDateTime payDate;

    private Long recieveCompanyId;

    private PaymentPurpose payUsage;

    @Null(groups = {UpdateGroup.class}, message = "付款金额不能更新")
    private BigDecimal payAmount;

    @NotNull(groups = {CreateGroup.class}, message = "付款方式不能为空")
    private PayMode payMode;

    @NotNull(groups = {CreateGroup.class}, message = "资金方不能为空")
    private Long capitalId;

    private BigDecimal useInterest;

    private Integer useDays;

    private LocalDateTime tsc;

    private static final long serialVersionUID = 1L;

    // for hs_ying_huikuan_map
    // for hs_ying_huankuan_map
    //
    private BigDecimal amount;
}

