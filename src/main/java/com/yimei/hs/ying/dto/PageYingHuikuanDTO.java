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



}
