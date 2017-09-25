package com.yimei.hs.user.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;

@Data
public class PagePartyDTO extends BaseFilter<PagePartyDTO> {
    private Integer partyType;
}
