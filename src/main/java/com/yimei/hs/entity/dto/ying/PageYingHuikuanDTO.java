package com.yimei.hs.entity.dto.ying;

import com.yimei.hs.boot.persistence.BaseFilter;
import com.yimei.hs.enums.PayMode;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by hary on 2017/9/21.
 */
@Data
public class PageYingHuikuanDTO extends BaseFilter<PageYingHuikuanDTO> {
    private Long orderId;
    private Long hsId;
    private Long huikuanCompanyId;
    private LocalDateTime huikuanDate;
    private PayMode huikuanMode;


//            <where>
//            <if test="orderId!=null">t1.orderId=#{orderId}</if>
//            <if test="hsId!=null">t1.hsId=#{hsId}</if>
//            <if test="huikuanCompanyId!=null">t1.huikuanCompanyId=#{huikuanCompanyId}</if>
//            <if test="huikuanMode!=null">t1.huikuanMode=#{huikuanMode}</if>
//            <if test="huikuanDate!=null">t1.huikuanDate=#{huikuanDate}</if>
//        </where>
    @Override
    public String getCountSql(String sql) {

        System.out.println("orignal sql = " + sql);
        StringBuilder sb = new StringBuilder("select * from hs_ying_huikuan ");
        if (orderId != null) {
            sb.append(" where orderId = ?");
        }
        if (hsId != null) {
            sb.append( " and hsId = ?");
        }
        if (huikuanCompanyId != null) {
            sb.append(" and huikuanCompanyId = ?");
        }
        if (huikuanDate != null) {
            sb.append(" and huikuanDate = ?");
        }
        if( huikuanMode != null) {
            sb.append(" and huikuanMode = ?");
        }
        String countSql = super.getCountSql(sb.toString());
        System.out.println("count sql = " + countSql);
        return countSql;
    }

}
