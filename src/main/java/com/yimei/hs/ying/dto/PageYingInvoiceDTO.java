package com.yimei.hs.ying.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import com.yimei.hs.enums.InvoiceType;
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

public class PageYingInvoiceDTO extends BaseFilter<PageYingInvoiceDTO> {

    private Long orderId;

    private Long hsId;

    private InvoiceType invoiceType;

    private LocalDateTime openDate;

    private Long openCompanyId;

    private Long receiverId;

    @Override
    public String getCountSql(String sql) {

        // System.out.println("orignal sql = " + sql);
        String nsql = new SQL() {
            {
                SELECT("*");
                FROM("hs_ying_invoice");
                if (orderId != null) { WHERE("orderId = ?"); }
                if (hsId != null) { WHERE("hsId = ?"); }
                if (invoiceType != null) { WHERE("invoiceType = ?"); }
                if (openDate != null) { WHERE( "openDate = ?"); }
                if (openCompanyId != null) { WHERE("openCompanyId = ?"); }
                if (receiverId != null) { WHERE("receiverId = ?"); }
            }
        }.toString();

        String countSql =  super.getCountSql(nsql);
        // System.out.println("count sql = " + countSql);
        return countSql;
    }

//    static public String getUrlTemplate() {
//        return "orderId=order"
//    }

}
