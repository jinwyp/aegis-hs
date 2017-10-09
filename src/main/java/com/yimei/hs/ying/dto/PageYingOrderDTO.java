package com.yimei.hs.ying.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import com.yimei.hs.enums.CargoType;
import com.yimei.hs.enums.OrderStatus;
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
public class PageYingOrderDTO extends BaseFilter<PageYingOrderDTO> {

    private Long deptId;

    private Long teamId;

    private Long creatorId;

    private Long ownerId;

    private Long mainAccounting;

    private CargoType cargoType;

    private Long upstreamId;

    private Long downstreamId;

    private OrderStatus status;

    //
    private LocalDateTime createDateStart;

    private LocalDateTime createDateEnd;

    @Override
    public String getCountSql(String sql) {
        String nsql = new SQL() {
            {
                SELECT("*");
                FROM("hs_ying_order");
                if (deptId != null) { WHERE("deptId = ?"); }
                if (teamId != null) { WHERE( "teamId = ?"); }
                if (creatorId != null ) { WHERE( "creatorId = ?"); }
                if (ownerId != null ) { WHERE( "ownerId = ?"); }
                if (mainAccounting != null ) { WHERE( "mainAccounting = ?"); }
                if (cargoType != null ) { WHERE( "cargoType = ?"); }
                if (upstreamId != null ) { WHERE( "upstreamId = ?"); }
                if (downstreamId != null ) { WHERE( "downstreamId = ?"); }
                if (status != null ) { WHERE( "status = ?"); }
                if (createDateStart != null) { WHERE( "tsc >= ?"); }
                if (createDateEnd != null) { WHERE( "tsc <= ?"); }
                WHERE( "deleted = 0");
            }
        }.toString();
        String countSql =  super.getCountSql(nsql);
        return countSql;
    }
}
