package com.yimei.hs.same.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
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
    private Boolean promise;
}
