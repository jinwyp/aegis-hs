package com.yimei.hs.entity.dto.ying;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;

/**
 * Created by hary on 2017/9/21.
 */
@Data
public class PageYingFeeDTO extends BaseFilter<PageYingFeeDTO> {
    private Long orderId;
}
