package com.yimei.hs.demo.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.ying.entity.YingFayun;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by hary on 2017/10/19.
 */

@RestController
@RequestMapping("/demo/business/{businessType}s")
public class Demo2Controller {

    /**
     * @return
     */
    @GetMapping
    public ResponseEntity<Result<Integer>> bt(
            @PathVariable("businessType") BusinessType businessType
    ) {
        return Result.ok(1);
    }

    /**
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Result<Integer>> bt2(
            @PathVariable("businessType") BusinessType businessType
    ) {
        System.out.println("business type = {}" +  businessType);
        return Result.ok(1);
    }

    /**
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<Result<Integer>> put(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("id") Long id
    ) {
        System.out.println("business type = {}" +  businessType);
        System.out.println("business id = {}" +  id);
        return Result.ok(1);
    }

    @PutMapping("/fayun")
    public ResponseEntity<Result<Integer>> valid(
            @PathVariable("businessType") BusinessType businessType,
            @RequestBody   @Validated  YingFayun yingFayun
            ) {
        System.out.println("business type = {}" +  businessType);
        return Result.ok(1);
    }
}
