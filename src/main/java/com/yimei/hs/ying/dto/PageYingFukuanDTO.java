package com.yimei.hs.ying.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by hary on 2017/9/21.
 */
@Data
public class PageYingFukuanDTO extends BaseFilter<PageYingFukuanDTO> {
    private Long orderId;
    private Long hsId;

    private LocalDateTime payDate;
    private Long recieveCompanyId;
}
