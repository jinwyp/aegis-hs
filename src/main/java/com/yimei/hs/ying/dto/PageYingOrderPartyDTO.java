package com.yimei.hs.ying.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import com.yimei.hs.enums.CargoType;
import com.yimei.hs.enums.CustomerType;
import com.yimei.hs.enums.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by hary on 2017/9/21.
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class PageYingOrderPartyDTO extends BaseFilter<PageYingOrderPartyDTO> {

    private Long orderId;
    private CustomerType custType;

    /**
     * @param sql
     * @return
     */
    @Override
    public String getCountSql(String sql) {
        System.out.println("orignal sql = " + sql);
        StringBuilder sb = new StringBuilder("select * from hs_ying_order_party ");
        if (orderId != null) {
            sb.append(" where orderId = ?");
        }
        if (custType != null) {
            sb.append(" where custType = ?");
        }
        String countSql = super.getCountSql(sb.toString());
        System.out.println("count sql = " + countSql);
        return countSql;
    }

}
