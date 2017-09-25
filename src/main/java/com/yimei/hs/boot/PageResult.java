package com.yimei.hs.boot;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class PageResult<T> {
    private Boolean success;
    private Page<T> data;
    private Error error;

    public static final <T> ResponseEntity<PageResult<T>> ok(Page<T> page) {
        return new ResponseEntity<>(new PageResult<>(true, page, null), HttpStatus.OK);
    }


    public static final <M> ResponseEntity<PageResult<M>> error(int code, String message) {
        return new ResponseEntity<>(new PageResult<M>(false, null, new Error(code, message)), HttpStatus.OK);
    }

    public static final <M> ResponseEntity<PageResult<M>> error(int code, String message, HttpStatus httpCode) {
        return new ResponseEntity<>(new PageResult<M>(false, null, new Error(code, message)), httpCode);
    }
}
