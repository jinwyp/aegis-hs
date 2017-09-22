package com.yimei.hs.entity.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;

@Data
public class PageTeamDTO extends BaseFilter<PageTeamDTO> {
    private long deptId;
}
