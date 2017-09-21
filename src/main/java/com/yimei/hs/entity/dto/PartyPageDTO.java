package com.yimei.hs.entity.dto;

import com.yimei.hs.boot.persistence.BaseFilter;
import lombok.Data;

@Data
public class PartyPageDTO extends BaseFilter<PartyPageDTO> {
    private Integer custType;
}
