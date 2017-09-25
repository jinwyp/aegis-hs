package com.yimei.hs.user.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PageUserDTO extends BaseFilter<PageUserDTO> {
    private Boolean isAdmin;
    private Long deptId;
    private Boolean isActive;
}
