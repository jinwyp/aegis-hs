package com.yimei.hs.controller;

import com.yimei.hs.entity.Party;
import com.yimei.hs.entity.dto.PageResult;
import com.yimei.hs.entity.dto.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RestController
@RequestMapping("/api")
public class PartyController {

    /**
     * 获取所有party
     * @return
     */
    @GetMapping("/partys")
    public ResponseEntity<PageResult<Party>> list() {
        return PageResult.ok(null);
    }

    /**
     * 获取party
     * @param id
     * @return
     */
    @GetMapping("/partys/{id}")
    public  ResponseEntity<Result<Party>> read(@PathVariable("id") long id) {
        return Result.ok(null);
    }

    /**
     * 创建party
     * @return
     */
    @PostMapping("/partys")
    public ResponseEntity<Result<Party>> create(@RequestBody Party party) {
        return Result.ok(null);
    }

    /**
     *  更新party
     * @return
     */
    @PutMapping("/partys/{id}")
    public ResponseEntity<Result<Integer>> update(@PathVariable("id") long id) {
        return Result.ok(1);
    }

}
