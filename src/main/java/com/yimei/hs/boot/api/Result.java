package com.yimei.hs.boot.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

/**
 * Created by hary on 2017/9/21.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class Result<T> {
    private Boolean success;

//    private Optional<T> data;
//    private Optional<Error> error;

    private T data;
    private Error error;

    public static final <M> ResponseEntity<Result<M>> error(int code, String message) {
        return new ResponseEntity<>(new Result<M>(false, null, new Error(code, message)), HttpStatus.BAD_REQUEST);
    }

    public static final <M> ResponseEntity<Result<M>> error(int code, String message, HttpStatus httpStatus) {
        return new ResponseEntity<>(new Result<M>(false, null, new Error(code, message)), httpStatus);
    }

    public static final <M> ResponseEntity<Result<M>> ok(M m) {
        ResponseEntity<Result<M>> entity=  new ResponseEntity<>(new Result<>(true, m, null), HttpStatus.OK);
        return entity;
    }

    /**
     * 创建成功
     * @param m
     * @param <M>
     * @return
     */
    public static final <M> ResponseEntity<Result<M>> created(M m) {
        ResponseEntity<Result<M>> entity = new ResponseEntity<>(new Result<>(true, m, null), HttpStatus.CREATED);
        return entity;
    }

    /**
     * 更新成功
     * @param m
     * @param <M>
     * @return
     */
    public static final <M> ResponseEntity<Result<M>> updated(M m) {
        ResponseEntity<Result<M>> entity = new ResponseEntity<>(new Result<>(true, m, null), HttpStatus.RESET_CONTENT);
        return entity;
    }

    public static final <M> ResponseEntity<Result<M>> ok(M m, HttpStatus httpStatus) {
        ResponseEntity<Result<M>> entity = new ResponseEntity<>(new Result<>(true, m, null), httpStatus);
        return entity;
    }

    public Result(int code, String msg) {
        this.error = new Error(code, msg);
        this.success = false;
    }
}


/**
 * Result<Team>(true, Team(1, , 3, 4))
 * Result<Team>(false, null, Error(50001, "asdfasfasafa"))
 * ResponseEntity<Result<Team>>()
 * <p>
 * Result<ArrayList<Team>>(true, Array(Team(1, 2, 3), Team(1,2, 3))
 * Result<ArrayList<Team>>(true, Array(Team(1, 2, 3), Team(1,2, 3))
 */
