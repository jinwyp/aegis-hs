package com.yimei.hs.entity.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;

@Data
public class PagePartyDTO extends BaseFilter<PagePartyDTO> {
    private Integer partyType;
}
