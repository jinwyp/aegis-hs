package com.yimei.hs.ying.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import com.yimei.hs.enums.FeeClass;
import lombok.Data;

/**
 * Created by hary on 2017/9/21.
 */
@Data
public class PageYingFeeDTO extends BaseFilter<PageYingFeeDTO> {
    private Long orderId;
    private Long hsId;
    private FeeClass name;
}
