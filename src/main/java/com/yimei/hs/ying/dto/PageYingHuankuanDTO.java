package com.yimei.hs.ying.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;
import org.apache.ibatis.jdbc.SQL;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by hary on 2017/9/21.
 */
@Data
public class PageYingHuankuanDTO extends BaseFilter<PageYingHuankuanDTO> {

    private Long orderId;
    private Long hsId;
    private Long skCompanyId;
    private LocalDateTime huankuanDate;
    private BigDecimal huankuanAmount;

    /**
     * <if test="orderId!=null">t1.orderId=#{orderId}</if>
     <if test="hsId!=null">t1.hsId=#{hsId}</if>
     <if test="skCompanyId!=null">t1.skCompanyId=#{skCompanyId}</if>
     <if test="huankuanDate!=null">t1.huankuanDate=#{huankuanDate}</if>
     <if test="huankuanAmount!=null">t1.huankuanAmount=#{huankuanAmount}</if>
     * @param sql
     * @return
     */
    @Override
    public String getCountSql(String sql) {

        String nsql = new SQL() {{
            SELECT("*");
            FROM("hs_ying_invoice");
            if (orderId != null) { WHERE("orderId = #{orderId"); }
            if (hsId != null) { WHERE("hsId = #{hsId}"); }
            if (skCompanyId != null) { WHERE("skCompanyId = #{skCompanyId}"); }
            if (huankuanDate != null) { WHERE("huankuanDate = #{huankuanDate}"); }
            if (huankuanAmount != null) { WHERE("huankuanAmount = #{huankuanAmount}"); }
        }}.toString();

        String countSql = super.getCountSql(nsql);
        System.out.println("count sql = " + countSql);
        return countSql;
    }

}
