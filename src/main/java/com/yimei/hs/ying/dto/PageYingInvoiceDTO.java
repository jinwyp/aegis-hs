package com.yimei.hs.ying.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;
import org.apache.ibatis.jdbc.SQL;

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

    private Long receiverId;

    @Override
    public String getCountSql(String sql) {

        System.out.println("orignal sql = " + sql);
        String nsql = new SQL() {
            {
                SELECT("*");
                FROM("hs_ying_invoice");
                if (orderId != null) { WHERE("orderId = #{orderId"); }
                if (hsId != null) { WHERE("hsId = #{hsId}"); }
                if (invoiceType != null) { WHERE("invoiceType = #{invoiceType}"); }
                if (openDate != null) { WHERE( "openDate = #{openDate}"); }
                if (openCompanyId != null) { WHERE("openCompanyId = #{openCompanyId}"); }
                if (receiverId != null) { WHERE("receiverId = #{receiverId}"); }
            }
        }.toString();

        String countSql =  super.getCountSql(nsql);
        System.out.println("count sql = " + countSql);
        return countSql;
    }

}
