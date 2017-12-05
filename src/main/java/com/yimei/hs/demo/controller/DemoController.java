package com.yimei.hs.demo.controller;

import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.cang.entity.ChukuInfo;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.same.entity.CapitalPressure;
import com.yimei.hs.ying.mapper.YingAnalysisDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hary on 2017/10/10.
 */

@RestController
@RequestMapping("/demo/{businessType}")
public class DemoController {


    /**
     * @return
     */
    @GetMapping(value = "/normal/{bt}s/abc")
    public ResponseEntity<Result<Integer>> bt(
            @PathVariable("bt") BusinessType businessType
    ) {
        return Result.ok(1);
    }


    /**
     * @return
     */
    @GetMapping(value = "/normal")
    public ResponseEntity<Result<Integer>> normal(
            @PathVariable("businessType") BusinessType businessType
            ) {
        return Result.ok(1);
    }

    /**
     * @return
     */
    @GetMapping(value = "/list")
    public ResponseEntity<Result<List<Integer>>> list() {
        return Result.ok(Arrays.asList(1, 2, 3));
    }

    /**
     * @return
     */
    @GetMapping(value = "/page")
    public ResponseEntity<Result<Page<Integer>>> page() {
        Page<Integer> page = Page.createInstance();
        page.setResults(Arrays.asList(1, 2, 3));
        page.setPageNo(1);
        page.setPageSize(3);
        page.setTotalPage(10);
        page.setTotalRecord(100);
        return Result.ok(page);
    }

}
