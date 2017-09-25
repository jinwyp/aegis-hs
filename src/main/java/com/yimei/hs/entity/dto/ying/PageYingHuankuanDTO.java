package com.yimei.hs.entity.dto.ying;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;

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

        System.out.println("orignal sql = " + sql);
        StringBuilder sb = new StringBuilder("select * from hs_ying_huankuan ");
        if (orderId != null) {
            sb.append(" where orderId = ?");
        }
        if (hsId != null) {
            sb.append( " and hsId = ?");
        }
        if (skCompanyId != null) {
            sb.append(" and skCompanyId = ?");
        }
        if (huankuanDate != null) {
            sb.append(" and huankuanDate = ?");
        }
        if( huankuanAmount != null) {
            sb.append(" and huankuanAmount = ?");
        }
        String countSql = super.getCountSql(sb.toString());
        System.out.println("count sql = " + countSql);
        return countSql;
    }

}
