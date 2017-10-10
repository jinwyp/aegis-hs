package com.yimei.hs.ying.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.ibatis.jdbc.SQL;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by hary on 2017/9/21.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)

public class PageYingHuankuanDTO extends BaseFilter<PageYingHuankuanDTO> {

    private Long orderId;
    private Long hsId;
    private Long skCompanyId;
    private LocalDateTime huankuanDate;
    private BigDecimal huankuanPrincipal;



}
