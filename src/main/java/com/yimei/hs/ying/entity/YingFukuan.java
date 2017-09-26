package com.yimei.hs.ying.entity;

import com.yimei.hs.enums.PayMode;
import com.yimei.hs.enums.PaymentPurpose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.security.DenyAll;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YingFukuan implements Serializable {
    private Long id;

    private Long orderId;

    private Long hsId;

    private LocalDateTime payDate;

    private Long recieveCompanyId;

    private PaymentPurpose payUsage;

    private BigDecimal payAmount;

    private PayMode payMode;

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

