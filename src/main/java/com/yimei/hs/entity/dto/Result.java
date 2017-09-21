package com.yimei.hs.entity.dto;

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
public class Result<T> {
    private Boolean success;

//    private Optional<T> data;
//    private Optional<Error> error;

    private T data;
    private Error error;

    public static final <M> ResponseEntity<Result<M>> error(int code, String message) {
        return new ResponseEntity<Result<M>>(new Result<M>(false, null, new Error(code, message)), HttpStatus.OK);
    }

    public static final <M> ResponseEntity<Result<M>> error(int code, String message, HttpStatus httpStatus) {
        return new ResponseEntity<Result<M>>(new Result<M>(false, null, new Error(code, message)), httpStatus);
    }

    public static final <T> ResponseEntity<Result<T>> ok(T t) {
        return new ResponseEntity<Result<T>>(new Result<T>(true, t, null), HttpStatus.OK);
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
