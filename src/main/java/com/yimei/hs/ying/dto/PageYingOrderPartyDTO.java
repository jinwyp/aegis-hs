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

}
