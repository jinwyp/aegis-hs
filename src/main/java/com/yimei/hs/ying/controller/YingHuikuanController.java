package com.yimei.hs.ying.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.PayMode;
import com.yimei.hs.enums.TrafficMode;
import com.yimei.hs.ying.entity.YingHuikuan;
import com.yimei.hs.boot.api.PageResult;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.ying.dto.PageYingHuikuanDTO;
import com.yimei.hs.ying.service.YingHuikuanService;
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
@RequestMapping("/api/ying")
@RestController
@Logined
public class YingHuikuanController {

    private static final Logger logger = LoggerFactory.getLogger(YingHuikuanController.class);

    @Autowired
    private YingHuikuanService yingHuikuanService;

    /**
     * 获取所有huikuan
     *
     * @return
     */
    @GetMapping("/{morderId}/huikuans")
    public ResponseEntity<Result<Page<YingHuikuan>>> list(
            @PathVariable("morderId") Long morderId,
            PageYingHuikuanDTO pageYingHuikuanDTO)
    {
        pageYingHuikuanDTO.setOrderId(morderId);
        return Result.ok(yingHuikuanService.getPage(pageYingHuikuanDTO));
    }

    /**
     * 获取huikuan
     *
     * @param id
     * @return
     */
    @GetMapping("/{morderId}/huikuans/{id}")
    public ResponseEntity<Result<YingHuikuan>> read(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        YingHuikuan huikuan = yingHuikuanService.findOne(id);
        if (huikuan == null) {
            return Result.error(4001, "记录不存在", HttpStatus.NOT_FOUND);
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
    public ResponseEntity<Result<YingHuikuan>> create(
            @PathVariable("morderId") Long morderId,
            @RequestBody @Validated(CreateGroup.class) YingHuikuan  yingHuikuan
    ) {
        if (yingHuikuan.getHuikuanMode().equals(PayMode.BANK_ACCEPTANCE) && yingHuikuan.getHuikuanBankPaper() == true) {

            if (yingHuikuan.getHuikuanBankPaperDate() == null) {
                return Result.error(4001, "银行承兑收到票据原件日期不能为空", HttpStatus.BAD_REQUEST);
            } else if (yingHuikuan.getHuikuanBankDiscount()!=null&&yingHuikuan.getHuikuanBankDiscount() == true &&
                    yingHuikuan.getHuikuanBankDiscountRate() == null) {
                return Result.error(4001, "银行承兑贴息率不能为空", HttpStatus.BAD_REQUEST);
            } else if (yingHuikuan.getHuikuanBankPaperExpire() == null) {
                return Result.error(4001, "银行承兑票据到期日不能为空", HttpStatus.BAD_REQUEST);
            } else if (yingHuikuan.getHuikuanBankDiscountRate() != null&&yingHuikuan.getHuikuanBankDiscountRate().compareTo(new BigDecimal(0)) == -1) {
                return Result.error(4001, "银行承兑贴息率不能为负数", HttpStatus.BAD_REQUEST);
            }

        } else if (yingHuikuan.getHuikuanMode().equals(PayMode.BUSINESS_ACCEPTANCE) && yingHuikuan.getHuikuanBusinessPaper() == true) {
            if (yingHuikuan.getHuikuanBusinessPaperDate() == null) {
                return Result.error(4001, "商业承兑收到票据原件日期不能为空", HttpStatus.BAD_REQUEST);
            } else if (yingHuikuan.getHuikuanBusinessDiscount()!=null&&yingHuikuan.getHuikuanBusinessDiscount() == true &&
                    yingHuikuan.getHuikuanBusinessDiscountRate() == null&&
                    yingHuikuan.getHuikuanBusinessDiscountRate().subtract(new BigDecimal(0)).doubleValue()<=0) {
                return Result.error(4001, "商业承兑贴息率不能为空", HttpStatus.BAD_REQUEST);
            } else if (yingHuikuan.getHuikuanBusinessPaperExpire() == null) {
                return Result.error(4001, "商业承兑票据到期日不能为空", HttpStatus.BAD_REQUEST);
            } else if (yingHuikuan.getHuikuanBusinessDiscountRate() != null&&yingHuikuan.getHuikuanBusinessDiscountRate().compareTo(new BigDecimal(0)) == -1) {
                return Result.error(4001, "商业承兑贴息率不能为负数", HttpStatus.BAD_REQUEST);
            }

        }

        yingHuikuan.setOrderId(morderId);
        int cnt = yingHuikuanService.create(yingHuikuan);
        if (cnt != 1) {
            logger.error("创建失败: {}", yingHuikuan);
            return Result.error(4001, "创建失败");
        }
        return Result.ok(yingHuikuan);
    }

    /**
     * 更新huikuan
     *
     * @return
     */
    @PutMapping("/{morderId}/huikuans/{id}")
    @Transactional(readOnly =  false)
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id,
            @RequestBody @Validated(UpdateGroup.class) YingHuikuan yingHuikuan
    ) {
        assert (yingHuikuan.getOrderId() == morderId);
        yingHuikuan.setId(id);
        int cnt = yingHuikuanService.update(yingHuikuan);
        if (cnt != 1) {
            return Result.error(4001, "更新失败");
        }
        return Result.ok(1);
    }

    /**
     * 逻辑删除
     * @param morderId
     * @param id
     * @return
     */
    @DeleteMapping("/{morderId}/huikuans/{id}")
    @Transactional(readOnly =  false)
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        int cnt = yingHuikuanService.delete(morderId, id);
        if (cnt != 1) {
            return Result.error(4001, "更新失败");
        }
        return Result.ok(1);
    }
}
