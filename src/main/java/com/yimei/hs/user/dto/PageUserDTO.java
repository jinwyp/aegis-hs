package com.yimei.hs.user.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;

@Data
public class PageUserDTO extends BaseFilter<PageUserDTO> {
    private Boolean isAdmin;
    private Long deptId;
    private Boolean isActive;
}
