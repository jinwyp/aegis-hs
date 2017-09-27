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
    @GetMapping("/{morderId}/invoices")
    public ResponseEntity<PageResult<YingInvoice>> list(
            @PathVariable("morderId") Long morderId,
            PageYingInvoiceDTO pageYingInvoiceDTO
    ) {
        pageYingInvoiceDTO.setOrderId(morderId);
        Page<YingInvoice> page = yingInvoiceService.getPage(pageYingInvoiceDTO);
        return PageResult.ok(page);
    }

    /**
     * 获取invoice
     *
     * @param id
     * @return
     */
    @GetMapping("/{morderId}/invoices/{id}")
    public ResponseEntity<Result<YingInvoice>> read(
            @PathVariable("morderId") Long morderId,
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
    @PostMapping("/{morderId}/invoices")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<YingInvoice>> create(
       @RequestBody @Validated(CreateGroup.class) YingInvoice yingInvoice
    ) {
        yingInvoiceService.create(yingInvoice);
        return Result.ok(yingInvoice);
    }

    /**
     * 更新invoice
     *
     * @return
     */
    @PutMapping("/{morderId}/invoices/{id}")
    @Transactional(readOnly =  false)
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id,
            @RequestBody @Validated(UpdateGroup.class) YingInvoice yingInvoice
    ) {
        assert (yingInvoice.getOrderId() == morderId);
        yingInvoice.setOrderId(morderId);
        int rtn = yingInvoiceService.update(yingInvoice);
        if (rtn != 1) {
            return Result.error(4001, "更新错误", HttpStatus.NOT_FOUND);
        }
        return Result.ok(1);
    }

    /**
     * 逻辑删除
     * @return
     */
    @DeleteMapping("/{morderId}/invoices/{id}")
    @Transactional(readOnly =  false)
    public ResponseEntity<Result<Integer>> delete(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        int rtn = yingInvoiceService.delete(id);
        if (rtn != 1) {
            return Result.error(4001, "删除错误", HttpStatus.NOT_FOUND);
        }
        return Result.ok(1);
    }


}
