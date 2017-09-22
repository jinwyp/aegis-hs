package com.yimei.hs.entity.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;

@Data
// @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class PageTeamDTO extends BaseFilter<PageTeamDTO> {
    private Long deptId;
}
