package com.yimei.hs.ying.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;
import org.apache.ibatis.jdbc.SQL;

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

        String nsql = new SQL() {{
            SELECT("*");
            FROM("hs_ying_invoice");
            if (orderId != null) { WHERE("orderId = #{orderId"); }
            if (hsId != null) { WHERE("hsId = #{hsId}"); }
            if (settleDate != null) { WHERE("settleDate = #{settleDate}"); }
        }}.toString();

        String countSql = super.getCountSql(nsql);
        System.out.println("count sql = " + countSql);
        return countSql;
    }

}
