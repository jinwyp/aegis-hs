package com.yimei.hs.entity.dto;

import com.yimei.hs.boot.persistence.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by hary on 2017/9/21.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    private Boolean success;
    private Page<T> data;
    private Error error;
}
