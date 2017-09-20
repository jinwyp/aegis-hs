package com.yimei.hs.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO {

    private Object responsedata;
    private int pageSize;
    private int pageNum;
    private int totalCount;
}
