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
        return getCountSqlForResultMap(sql);
    }

}
