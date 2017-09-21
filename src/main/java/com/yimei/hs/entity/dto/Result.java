package com.yimei.hs.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

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

    private Optional<T> data;
    private Optional<Error> error;

}


/**
 *    Result<Team>(true, Team(1, , 3, 4))
 *    Result<Team>(false, null, Error(50001, "asdfasfasafa"))
 *    ResponseEntity<Result<Team>>()
 *
 *    Result<ArrayList<Team>>(true, Array(Team(1, 2, 3), Team(1,2, 3))
 *    Result<ArrayList<Team>>(true, Array(Team(1, 2, 3), Team(1,2, 3))
 *
 */
