package com.yimei.hs.same.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.enums.CargoType;
import com.yimei.hs.enums.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Created by hary on 2017/9/21.
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class PageOrderDTO extends BaseFilter<PageOrderDTO> {

    private BusinessType businessType;

    private Long deptId;

    private Long teamId;

    private Long creatorId;

    private Long ownerId;

    private Long mainAccounting;

    private CargoType cargoType;

    private Long upstreamId;

    private Long downstreamId;

    private OrderStatus status;


    private LocalDateTime createDateStart;

    private LocalDateTime createDateEnd;

}
