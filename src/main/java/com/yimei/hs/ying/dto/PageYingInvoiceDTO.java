package com.yimei.hs.ying.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by hary on 2017/9/21.
 */
@Data
public class PageYingInvoiceDTO extends BaseFilter<PageYingInvoiceDTO> {

    private Long orderId;

    private Long hsId;

    private String invoiceType;

    private LocalDateTime openDate;

    private Long openCompanyId;

    private Long recieverId;

    private LocalDateTime tsc;

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
        if (invoiceType != null) {
            sb.append(" and invoiceType = ?");
        }
        if (openDate != null) {
            sb.append(" and openDate = ?");
        }
        if (openCompanyId != null) {
            sb.append(" and openCompanyId = ?");
        }
        if (recieverId != null) {
            sb.append(" and recieverId = ?");
        }

        String countSql = super.getCountSql(sb.toString());
        System.out.println("count sql = " + countSql);
        return countSql;
    }

}
