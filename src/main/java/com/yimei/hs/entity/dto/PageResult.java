package com.yimei.hs.entity.dto;

import com.yimei.hs.boot.persistence.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    public static final <T> ResponseEntity<PageResult<T>> ok(Page<T> page) {
        return new ResponseEntity<PageResult<T>>(new PageResult<>(true, page, null), HttpStatus.OK);
    }
}
