package com.yimei.hs.user.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PagePartyDTO extends BaseFilter<PagePartyDTO> {
    private Integer partyType;
}
