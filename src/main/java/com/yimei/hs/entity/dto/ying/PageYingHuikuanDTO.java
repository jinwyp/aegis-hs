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


    @Override
    public String getCountSql(String sql) {

//        System.out.println("orignal sql = " + sql);
//        StringBuilder sb = new StringBuilder("select * from hs_ying_huikuan ");
//        if (orderId != null) {
//            sb.append(" where orderId = ?");
//        }
//        String countSql = super.getCountSql(sb.toString());
//        System.out.println("count sql = " + countSql);
//        return countSql;

        return getCountSqlForResultMap(sql);

    }

}
