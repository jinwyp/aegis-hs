package com.yimei.hs.ying.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.entity.YingInvoice;
import com.yimei.hs.boot.api.PageResult;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.ying.dto.PageYingInvoiceDTO;
import com.yimei.hs.ying.service.YingInvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api/ying")
@RestController
@Logined
public class YingInvoiceController {

    private static final Logger logger = LoggerFactory.getLogger(YingInvoiceController.class);

    @Autowired
    private YingInvoiceService yingInvoiceService;

    /**
     * 获取所有invoice
     *
     * @return
     */
    @GetMapping("/{orderId}/invoices")
    public ResponseEntity<PageResult<YingInvoice>> list(
            @PathVariable("orderId") Long orderId,
            PageYingInvoiceDTO pageYingInvoiceDTO
    ) {
        pageYingInvoiceDTO.setOrderId(orderId);
        Page<YingInvoice> page = yingInvoiceService.getPage(pageYingInvoiceDTO);
        return PageResult.ok(page);
    }

    /**
     * 获取invoice
     *
     * @param id
     * @return
     */
    @GetMapping("/{orderId}/invoices/{id}")
    public ResponseEntity<Result<YingInvoice>> read(
            @PathVariable("orderId") long orderId,
            @PathVariable("id") long id
    ) {
        YingInvoice invoice = yingInvoiceService.findOne(id);
        if (invoice == null) {
            return Result.error(4001, "记录不存在", HttpStatus.NOT_FOUND);
        } else {
            return Result.ok(invoice);
        }
    }

    /**
     * 创建invoice
     *
     * @return
     */
    @PostMapping("/{orderId}/invoices")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<YingInvoice>> create(
       @RequestBody @Validated(CreateGroup.class) YingInvoice yingInvoice
    ) {
        // 创建发表记录 todo
        yingInvoiceService.create(yingInvoice);
        return Result.ok(yingInvoice);
    }

    /**
     * 更新invoice
     *
     * @return
     */
    @PutMapping("/{orderId}/invoice/:id")
    @Transactional(readOnly =  false)
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("orderId") long orderId,
            @PathVariable("id") long id,
            @RequestBody @Validated(UpdateGroup.class) YingInvoice yingInvoice
    ) {
        assert (yingInvoice.getOrderId() == orderId);
        yingInvoice.setOrderId(orderId);
        int rtn = yingInvoiceService.update(yingInvoice);
        if (rtn != 1) {
            return Result.error(4001, "更新错误");
        }
        return Result.ok(1);
    }

}
