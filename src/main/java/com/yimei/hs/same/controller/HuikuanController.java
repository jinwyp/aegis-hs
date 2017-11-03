package com.yimei.hs.same.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.enums.PayMode;
import com.yimei.hs.same.dto.PageHuikuanDTO;
import com.yimei.hs.same.entity.Huikuan;
import com.yimei.hs.same.service.HuikuanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api/business/{businessType}")
@RestController
@Logined
public class HuikuanController {

    private static final Logger logger = LoggerFactory.getLogger(HuikuanController.class);

    @Autowired
    private HuikuanService huikuanService;

    /**
     * 获取所有huikuan
     *
     * @return
     */
    @GetMapping("/{morderId}/huikuans")
    public ResponseEntity<Result<Page<Huikuan>>> list(
           @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            PageHuikuanDTO pageHuikuanDTO) {
        pageHuikuanDTO.setOrderId(morderId);
        return Result.ok(huikuanService.getPage(pageHuikuanDTO));
    }

    /**
     * 获取huikuan
     *
     * @param id
     * @return
     */
    @GetMapping("/{morderId}/huikuans/{id}")
    public ResponseEntity<Result<Huikuan>> read(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        Huikuan huikuan = huikuanService.findOne(id);
        if (huikuan == null) {
            return Result.error(4001, "记录不存在", HttpStatus.BAD_REQUEST);
        } else {
            return Result.ok(huikuan);
        }
    }

    /**
     * 创建huikuan
     *
     * @return
     */
    @PostMapping("/{morderId}/huikuans")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<Huikuan>> create(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @RequestBody @Validated(CreateGroup.class) Huikuan huikuan
    ) {

        if (huikuan.getHuikuanAmount().compareTo(BigDecimal.ZERO) == 1 && huikuan.getHuikuanAmount().compareTo(new BigDecimal("99999999.99")) <= 0) {

            if (huikuan.getHuikuanMode().equals(PayMode.BANK_ACCEPTANCE) && huikuan.getHuikuanBankPaper() == true) {

                if (huikuan.getHuikuanBankPaperDate() == null) {
                    return Result.error(4001, "银行承兑收到票据原件日期不能为空", HttpStatus.BAD_REQUEST);
                } else if (huikuan.getHuikuanBankDiscount() != null && huikuan.getHuikuanBankDiscount() == true &&
                        huikuan.getHuikuanBankDiscountRate() == null) {
                    return Result.error(4001, "银行承兑贴息率不能为空", HttpStatus.BAD_REQUEST);
                } else if (huikuan.getHuikuanBankPaperExpire() == null) {
                    return Result.error(4001, "银行承兑票据到期日不能为空", HttpStatus.BAD_REQUEST);
                } else if (huikuan.getHuikuanBankDiscountRate() != null && huikuan.getHuikuanBankDiscountRate().compareTo(new BigDecimal(0)) == -1) {
                    return Result.error(4001, "银行承兑贴息率不能为负数", HttpStatus.BAD_REQUEST);
                }

            } else if (huikuan.getHuikuanMode().equals(PayMode.BUSINESS_ACCEPTANCE) && huikuan.getHuikuanBusinessPaper() == true) {
                if (huikuan.getHuikuanBusinessPaperDate() == null) {
                    return Result.error(4001, "商业承兑收到票据原件日期不能为空", HttpStatus.BAD_REQUEST);
                } else if (huikuan.getHuikuanBusinessDiscount() != null && huikuan.getHuikuanBusinessDiscount() == true &&
                        huikuan.getHuikuanBusinessDiscountRate() == null &&
                        huikuan.getHuikuanBusinessDiscountRate().subtract(new BigDecimal(0)).doubleValue() <= 0) {
                    return Result.error(4001, "商业承兑贴息率不能为空", HttpStatus.BAD_REQUEST);
                } else if (huikuan.getHuikuanBusinessPaperExpire() == null) {
                    return Result.error(4001, "商业承兑票据到期日不能为空", HttpStatus.BAD_REQUEST);
                } else if (huikuan.getHuikuanBusinessDiscountRate() != null && huikuan.getHuikuanBusinessDiscountRate().compareTo(new BigDecimal(0)) == -1) {
                    return Result.error(4001, "商业承兑贴息率不能为负数", HttpStatus.BAD_REQUEST);
                }

            }

            huikuan.setOrderId(morderId);
            int cnt = huikuanService.create(huikuan);
            if (cnt != 1) {
                logger.error("创建失败: {}", huikuan);
                return Result.error(4001, "创建失败");
            }
            return Result.ok(huikuan);
        } else {
            return Result.error(4001, "输入非法");
        }
    }

    /**
     * 更新huikuan
     *
     * @return
     */
    @PutMapping("/{morderId}/huikuans/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id,
            @RequestBody @Validated(UpdateGroup.class) Huikuan huikuan
    ) {
        assert (huikuan.getOrderId() == morderId);
        huikuan.setId(id);
        int cnt = huikuanService.update(huikuan);
        if (cnt != 1) {
            return Result.error(4001, "更新失败");
        }
        return Result.ok(1);
    }

    /**
     * 逻辑删除
     *
     * @param morderId
     * @param id
     * @return
     */
    @DeleteMapping("/{morderId}/huikuans/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        int cnt = huikuanService.delete(id,morderId );
        if (cnt != 1) {
            return Result.error(4001, "删除失败");
        }
        return Result.ok(1);
    }
}
