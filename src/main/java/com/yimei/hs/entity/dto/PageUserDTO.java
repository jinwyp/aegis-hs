package com.yimei.hs.entity.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;

@Data
public class PageUserDTO extends BaseFilter<PageUserDTO> {
    private Boolean isAdmin;
    private Long deptId;
    private Boolean isActive;
}
