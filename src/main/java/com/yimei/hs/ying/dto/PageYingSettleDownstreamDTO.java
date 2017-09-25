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

    /**
     * @param sql
     * @return
     */
    @Override
    public String getCountSql(String sql) {

        System.out.println("orignal sql = " + sql);
        StringBuilder sb = new StringBuilder("select * from hs_ying_settle_downstream ");
        if (orderId != null) {
            sb.append(" where orderId = ?");
        }
        if (hsId != null) {
            sb.append( " and hsId = ?");
        }
        if (settleDate != null) {
            sb.append(" and settleDate = ?");
        }

        String countSql = super.getCountSql(sb.toString());
        System.out.println("count sql = " + countSql);
        return countSql;
    }

}
