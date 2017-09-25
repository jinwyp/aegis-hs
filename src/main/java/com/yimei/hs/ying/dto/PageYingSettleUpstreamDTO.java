package com.yimei.hs.ying.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Created by hary on 2017/9/21.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class PageYingSettleUpstreamDTO extends BaseFilter<PageYingSettleUpstreamDTO>{
    private Long orderId;
    private Long hsId;
    private LocalDateTime settleDate;
}
