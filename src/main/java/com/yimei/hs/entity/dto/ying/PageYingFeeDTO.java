package com.yimei.hs.entity.dto.ying;

import com.yimei.hs.boot.persistence.BaseFilter;
import com.yimei.hs.enums.FeeClass;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by hary on 2017/9/21.
 */
@Data
public class PageYingFeeDTO extends BaseFilter<PageYingFeeDTO> {
    private Long orderId;
    private Long hsId;
    private FeeClass name;
}
