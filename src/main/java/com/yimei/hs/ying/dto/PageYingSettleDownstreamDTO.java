package com.yimei.hs.ying.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by hary on 2017/9/21.
 */


@Data
public class PageYingSettleDownstreamDTO extends BaseFilter<PageYingSettleDownstreamDTO> {
    private Long orderId;
    private Long hsId;
    private LocalDateTime settleDate;

    @Override
    public String getCountSql(String sql) {
        return getCountSqlForResultMap(sql);
    }

}
