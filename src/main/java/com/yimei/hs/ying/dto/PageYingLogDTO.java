package com.yimei.hs.ying.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * Created by hary on 2017/9/21.
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)

public class PageYingLogDTO extends BaseFilter<PageYingLogDTO> {

    @NotNull(message = "业务线不能为空")
    private Long orderId;

    private Long hsId;

    @NotNull(message = "实体类型不能为空")
    private String entityType;
}
