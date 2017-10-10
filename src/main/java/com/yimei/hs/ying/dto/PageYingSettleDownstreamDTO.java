package com.yimei.hs.ying.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.ibatis.jdbc.SQL;

import java.time.LocalDateTime;

/**
 * Created by hary on 2017/9/21.
 */


@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)

public class PageYingSettleDownstreamDTO extends BaseFilter<PageYingSettleDownstreamDTO> {
    private Long orderId;
    private Long hsId;
    private LocalDateTime settleDate;


}
