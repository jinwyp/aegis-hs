package com.yimei.hs.controller.ying;

import com.yimei.hs.entity.YingInvoice;
import com.yimei.hs.entity.dto.PageResult;
import com.yimei.hs.entity.dto.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api/ying")
@RestController
public class YingInvoiceController {

    /**
     * 获取所有invoice
     *
     * @return
     */
    @GetMapping("/{orderId}/invoices")
    public ResponseEntity<PageResult<YingInvoice>> list() {
        return PageResult.ok(null);
    }

    /**
     * 获取invoice
     *
     * @param id
     * @return
     */
    @GetMapping("/{orderId}/invoices/:id")
    public ResponseEntity<Result<YingInvoice>> read(
            @PathVariable("orderId") long orderId,
            @PathVariable("id") long id
    ) {
        return Result.ok(null);
    }

    /**
     * 创建invoice
     *
     * @return
     */
    @PostMapping("/{orderId}/invoices")
    public ResponseEntity<Result<YingInvoice>> create(@PathVariable("orderId") long orderId) {
        return Result.ok(null);
    }

    /**
     * 更新invoice
     *
     * @return
     */
    @PutMapping("/{orderId}/invoice/:id")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("orderId") long orderId,
            @PathVariable("id") long id
    ) {
        return Result.ok(1);
    }

}
