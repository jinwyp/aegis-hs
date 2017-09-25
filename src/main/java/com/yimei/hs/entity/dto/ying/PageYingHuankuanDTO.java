package com.yimei.hs.entity.dto.ying;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;

/**
 * Created by hary on 2017/9/21.
 */
@Data
public class PageYingHuankuanDTO extends BaseFilter<PageYingHuankuanDTO> {
    private Long orderId;
    private Long hsId;

    @Override
    public String getCountSql(String sql) {
        return getCountSqlForResultMap(sql);
    }

}
