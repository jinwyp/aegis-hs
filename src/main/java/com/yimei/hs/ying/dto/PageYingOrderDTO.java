package com.yimei.hs.ying.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import com.yimei.hs.enums.CargoType;
import com.yimei.hs.enums.OrderStatus;
import com.yimei.hs.ying.controller.YingOrderController;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hary on 2017/9/21.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class PageYingOrderDTO extends BaseFilter<PageYingOrderDTO> {
    private static final Logger logger = LoggerFactory.getLogger(YingOrderController.class);

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

        logger.error("sql===============>"+sql);
        String countSql =  super.getCountSql(nsql);
        System.out.println("count sql = " + countSql);
        return countSql;
    }
}
