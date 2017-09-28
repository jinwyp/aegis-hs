package com.yimei.hs.ying.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import com.yimei.hs.enums.PayMode;
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


        String nsql = new SQL() {{
            SELECT("*");
            FROM("hs_ying_huikuan");
            if (orderId != null) { WHERE("orderId = ?"); }
            if (hsId != null) { WHERE("hsId = ?"); }
            if (huikuanCompanyId != null) { WHERE("huikuanCompanyId = ?"); }
            if (huikuanDate != null) { WHERE("huikuanDate = ?"); }
            if (huikuanMode != null) { WHERE("huikuanMode = ?"); }
            WHERE( "deleted = 0");
        }}.toString();

        String countSql = super.getCountSql(nsql);
        // System.out.println("count sql = " + countSql);
        return countSql;
    }

}
