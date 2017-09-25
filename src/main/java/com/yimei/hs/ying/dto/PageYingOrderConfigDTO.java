package com.yimei.hs.ying.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;

/**
 * Created by hary on 2017/9/25.
 */
@Data
public class PageYingOrderConfigDTO extends BaseFilter<PageYingOrderConfigDTO> {
    private Long orderId;
}
