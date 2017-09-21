package com.yimei.hs.entity.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by hary on 2017/9/21.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class ListResult<T> {
    private Boolean success;
    private List<T> data;
    private Error error;

    public static final <T> ResponseEntity<ListResult<T>> ok(List<T> list) {
        return new ResponseEntity<>(new ListResult<>(true, list, null), HttpStatus.OK);
    }


    public static final <M> ResponseEntity<ListResult<M>> error(int code, String message) {
        return new ResponseEntity<>(new ListResult<M>(false, null, new Error(code, message)), HttpStatus.OK);
    }

    public static final <M> ResponseEntity<ListResult<M>> error(int code, String message, HttpStatus httpCode) {
        return new ResponseEntity<>(new ListResult<M>(false, null, new Error(code, message)), httpCode);
    }
}
