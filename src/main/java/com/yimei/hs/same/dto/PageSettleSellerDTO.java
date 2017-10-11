package com.yimei.hs.same.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Created by hary on 2017/9/21.ad
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class PageSettleSellerDTO extends BaseFilter<PageSettleSellerDTO>{
    private Long orderId;
    private Long hsId;
    private LocalDateTime settleDate;
}
