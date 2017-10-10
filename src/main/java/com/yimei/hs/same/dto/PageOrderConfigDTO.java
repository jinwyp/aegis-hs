package com.yimei.hs.same.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by hary on 2017/9/25.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)

public class PageOrderConfigDTO extends BaseFilter<PageOrderConfigDTO> {
    private Long orderId;
}
