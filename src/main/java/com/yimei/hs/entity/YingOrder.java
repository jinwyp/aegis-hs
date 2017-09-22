package com.yimei.hs.entity;

import com.yimei.hs.enums.CargoType;
import com.yimei.hs.enums.SettleMode;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class YingOrder implements Serializable {
    private Long id;

    private Long deptId;

    private Long teamId;

    private Long creatorId;

    private Long ownerId;

    private Long mainAccounting;

    private String line;

    private CargoType cargoType;

    private Long upstreamId;

    private SettleMode upstreamSettleMode;

    private Long downstreamId;

    private SettleMode downstreamSettleMode;

    private String status;

    private LocalDateTime tsc;


    private List<YingOrderConfig> orderConfigList;   // 关联的核算月配置



    private List<YingOrderParty> orderPartyList; // 关联的参与方



    private static final long serialVersionUID = 1L;

}