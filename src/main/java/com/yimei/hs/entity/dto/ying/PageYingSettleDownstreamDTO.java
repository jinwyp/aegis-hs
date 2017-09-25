package com.yimei.hs.entity.dto.ying;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by hary on 2017/9/21.
 */


@Data
public class PageYingSettleDownstreamDTO extends BaseFilter<PageYingSettleDownstreamDTO> {
    private Long orderId;
    private Long hsId;
    private LocalDateTime settleDate;
}
