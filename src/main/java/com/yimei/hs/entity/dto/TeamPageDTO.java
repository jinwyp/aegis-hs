package com.yimei.hs.entity.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;

@Data
public class TeamPageDTO extends BaseFilter<TeamPageDTO> {
    private long deptId;
}
