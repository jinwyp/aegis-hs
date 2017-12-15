package com.yimei.hs.user.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class PageUserTeamMapDTO extends BaseFilter<PageUserTeamMapDTO> {

    private Long id;

    private Long userId;

    private Long teamId;

    private int  deleted;
}
