package com.yimei.hs.entity.dto.ying;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;

/**
 * Created by hary on 2017/9/21.
 */
@Data
public class PageYingFukuanDTO extends BaseFilter<PageYingFukuanDTO> {
    private Long orderId;
}
