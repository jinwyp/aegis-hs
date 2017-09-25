package com.yimei.hs.entity.dto.ying;

import com.yimei.hs.boot.persistence.BaseFilter;
import com.yimei.hs.enums.CargoType;
import com.yimei.hs.enums.OrderStatus;
import com.yimei.hs.enums.SettleMode;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by hary on 2017/9/21.
 */

@Data
public class PageYingOrderDTO extends BaseFilter<PageYingOrderDTO> {

    private Long deptId;

    private Long teamId;

    private Long creatorId;

    private Long ownerId;

    private Long mainAccounting;

    private String line;

    private CargoType cargoType;

    private Long upstreamId;

    private Long downstreamId;

    private OrderStatus status;

    @Override
    public String getCountSql(String sql) {
        return getCountSqlForResultMap(sql);
    }
}
