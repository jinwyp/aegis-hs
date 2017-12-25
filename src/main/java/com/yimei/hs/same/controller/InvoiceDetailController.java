package com.yimei.hs.same.controller;

import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.same.service.InvoiceDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api/business/{businessType}")
@RestController
@Logined
public class InvoiceDetailController {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceDetailController.class);

    @Autowired
    private InvoiceDetailService invoiceDetailService;

    /**
     * 逻辑删除
     *
     * @return
     */
    @DeleteMapping("/{morderId}/invoicesDetail/{id}")
    public ResponseEntity<Result<Integer>> delete(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        int rtn = invoiceDetailService.delete(id);
        if (rtn != 1) {
            return Result.error(4001, "删除错误");
        }
        return Result.ok(1);
    }


}
