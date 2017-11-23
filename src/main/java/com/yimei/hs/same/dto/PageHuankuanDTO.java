package com.yimei.hs.same.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by hary on 2017/9/21.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)

public class PageHuankuanDTO extends BaseFilter<PageHuankuanDTO> {
    private Long orderId;
    private Long hsId;
    private LocalDateTime huankuanDate;
    private BigDecimal huankuanPrincipal;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate huankuankDateStart ;  //还款起始日期

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate huankuankDateEnd   ; // 还款结束日期

    private Boolean promise;
}
