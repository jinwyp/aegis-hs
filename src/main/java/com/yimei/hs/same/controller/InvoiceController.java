package com.yimei.hs.same.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.same.dto.PageInvoiceDTO;
import com.yimei.hs.same.entity.Invoice;
import com.yimei.hs.same.service.InvoiceService;
import com.yimei.hs.same.service.OrderPartyService;
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
@RequestMapping("/api/business/{businessType}")

@RestController
@Logined
public class InvoiceController {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private OrderPartyService orderPartyService;
    /**
     * 获取所有invoice
     *
     * @return
     */
    @GetMapping("/{morderId}/invoices")
    public ResponseEntity<Result<Page<Invoice>>> list(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            PageInvoiceDTO pageInvoiceDTO
    ) {
        pageInvoiceDTO.setOrderId(morderId);
        Page<Invoice> page = invoiceService.getPage(pageInvoiceDTO);
        logger.info("my invoice page = {}", page);
        return Result.ok(page);
    }

    /**
     * 获取invoice
     *
     * @param id
     * @return
     */
    @GetMapping("/{morderId}/invoices/{id}")
    public ResponseEntity<Result<Invoice>> read(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        Invoice invoice = invoiceService.findOne(id);
        if (invoice == null) {
            return Result.error(4001, "记录不存在");
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
    public ResponseEntity<Result<Invoice>> create(
            @PathVariable("businessType") BusinessType businessType,
            @RequestBody @Validated(CreateGroup.class) Invoice yingInvoice
    ) {



        int rtn = invoiceService.create(yingInvoice);
        if (rtn != 1) {
            return Result.error(4001, "创建失败");
        }
        return Result.ok(yingInvoice);
    }

    /**
     * 更新invoice
     *
     * @return
     */
    @PutMapping("/{morderId}/invoices/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id,
            @RequestBody @Validated(UpdateGroup.class) Invoice yingInvoice
    ) {
        assert (yingInvoice.getOrderId() == morderId);
        yingInvoice.setOrderId(morderId);
        int rtn = invoiceService.update(yingInvoice);
        if (rtn != 1) {
            return Result.error(4001, "更新错误");
        }
        return Result.ok(1);
    }

    /**
     * 逻辑删除
     *
     * @return
     */
    @DeleteMapping("/{morderId}/invoices/{id}")
    public ResponseEntity<Result<Integer>> delete(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        int rtn = invoiceService.delete(id);
        if (rtn != 1) {
            return Result.error(4001, "删除错误");
        }
        return Result.ok(1);
    }


}
