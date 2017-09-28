package com.yimei.hs.ying.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.ibatis.jdbc.SQL;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by hary on 2017/9/21.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)

public class PageYingHuankuanDTO extends BaseFilter<PageYingHuankuanDTO> {

    private Long orderId;
    private Long hsId;
    private Long skCompanyId;
    private LocalDateTime huankuanDate;
    private BigDecimal huankuanAmount;

    /**
     * @param sql
     * @return
     */
    @Override
    public String getCountSql(String sql) {

        String nsql = new SQL() {{
            SELECT("*");
            FROM("hs_ying_huankuan");
            if (orderId != null) { WHERE("orderId = ?"); }
            if (hsId != null) { WHERE("hsId = ?"); }
            if (skCompanyId != null) { WHERE("skCompanyId = ?"); }
            if (huankuanDate != null) { WHERE("huankuanDate =?"); }
            if (huankuanAmount != null) { WHERE("huankuanAmount = ?"); }
            WHERE( "deleted = 0");
        }}.toString();

        String countSql = super.getCountSql(nsql);
        // System.out.println("count sql = " + countSql);
        return countSql;
    }

}
