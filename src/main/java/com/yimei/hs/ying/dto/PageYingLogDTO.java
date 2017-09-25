package com.yimei.hs.ying.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;

/**
 * Created by hary on 2017/9/21.
 */

@Data
public class PageYingLogDTO extends BaseFilter<PageYingLogDTO> {
    private Long orderId;
    private Long hsId;
    private String entityType;
}
