package com.yimei.hs.ying.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import com.yimei.hs.enums.CargoType;
import com.yimei.hs.enums.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.jdbc.SQL;

/**
 * Created by hary on 2017/9/21.
 */

@Data
@EqualsAndHashCode(callSuper = false)
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

    @Override
    public String getCountSql(String sql) {
        String nsql = new SQL() {
            {
                SELECT("*");
                FROM("hs_ying_order");
                if (deptId != null) { WHERE("deptId = #{deptId}"); }
                if (teamId != null) { WHERE( "teamId = #{teamId}"); }
                if (creatorId != null ) { WHERE( "creatorId = #{creatorId}"); }
                if (ownerId != null ) { WHERE( "ownerId = #{ownerId}"); }
                if (mainAccounting != null ) { WHERE( "mainAccounting = #{mainAccounting}"); }
                if (cargoType != null ) { WHERE( "cargoType = #{cargoType}"); }
                if (upstreamId != null ) { WHERE( "upstreamId = #{upstreamId}"); }
                if (downstreamId != null ) { WHERE( "downstreamId = #{downstreamId}"); }
                if (status != null ) { WHERE( "status = #{status}"); }
            }
        }.toString();

        String countSql =  super.getCountSql(nsql);
        System.out.println("count sql = " + countSql);
        return countSql;
    }
}
