package com.yimei.hs.same.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.cang.dto.CangAnalysisData;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.same.dto.PageFukuanDTO;
import com.yimei.hs.same.entity.Fukuan;
import com.yimei.hs.same.entity.Order;
import com.yimei.hs.same.service.DataAnalysisService;
import com.yimei.hs.same.service.FukuanService;
import com.yimei.hs.same.service.JiekuanService;
import com.yimei.hs.same.service.OrderService;
import com.yimei.hs.ying.entity.YingAnalysisData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api/business/{businessType}")
@RestController
@Logined
public class FukuanController {

    private static final Logger logger = LoggerFactory.getLogger(FukuanController.class);


    @Autowired
    private FukuanService fukuanService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private DataAnalysisService dataAnalysisService;

    /**
     * 获取付款-分页
     *
     * @return
     */
    @GetMapping("/{morderId}/fukuans")
    public ResponseEntity<Result<Page<Fukuan>>> list(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            PageFukuanDTO pageFukuanDTO) {

        pageFukuanDTO.setOrderId(morderId);
        Page<Fukuan> page = fukuanService.getPage(pageFukuanDTO);

        return Result.ok(page);
    }

    /**
     * @param businessType
     * @param morderId
     * @return
     */
    @GetMapping("/{morderId}/fukuansUnfinished")
    public ResponseEntity<Result<List<Fukuan>>> list(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId) {
        return Result.ok(fukuanService.getListUnfinished(morderId));
    }


    /**
     * 获取付款
     *
     * @param id
     * @return
     */
    @GetMapping("/{morderId}/fukuans/{id}")
    public ResponseEntity<Result<Fukuan>> read(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        Fukuan fukuan = fukuanService.findOne(id);
        if (fukuan == null) {
            return Result.error(4001, "记录不存在", HttpStatus.BAD_REQUEST);
        } else {
            return Result.ok(fukuan);
        }
    }

    /**
     * 创建付款
     *
     * @return
     */
    @PostMapping("/{morderId}/fukuans")
    public ResponseEntity<Result<Fukuan>> create(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @RequestBody @Validated(CreateGroup.class) Fukuan fukuan
    ) {

        BigDecimal totalPaymentMoney = fukuan.getPayAmount();
        BigDecimal purchaseCargoAmountofMoney = new BigDecimal("0");

        if (businessType.equals(BusinessType.ying)) {
            YingAnalysisData yingAnalysisData = dataAnalysisService.findOneYing(morderId, fukuan.getHsId());
            totalPaymentMoney = totalPaymentMoney.add((yingAnalysisData.getTotalPaymentAmount() == null ? new BigDecimal("0") : yingAnalysisData.getTotalPaymentAmount()));
            purchaseCargoAmountofMoney = purchaseCargoAmountofMoney.add((yingAnalysisData.getPurchaseCargoAmountofMoney() == null ? new BigDecimal("0") : yingAnalysisData.getPurchaseCargoAmountofMoney()));
        } else if (businessType.equals(BusinessType.cang)) {
            CangAnalysisData cangAnalysisData = dataAnalysisService.findOneCang(morderId, fukuan.getHsId());
            totalPaymentMoney = totalPaymentMoney.add((cangAnalysisData.getTotalPaymentAmount() == null ? new BigDecimal("0") : cangAnalysisData.getTotalPaymentAmount()));
            purchaseCargoAmountofMoney = purchaseCargoAmountofMoney.add((cangAnalysisData.getPurchaseCargoAmountofMoney() == null ? new BigDecimal("0") : cangAnalysisData.getPurchaseCargoAmountofMoney()));
        }

        if (purchaseCargoAmountofMoney != null) {
            // 付款的限制条件：付款总金额 < 当前计算出的采购货款总额
            if (purchaseCargoAmountofMoney.compareTo(totalPaymentMoney) == 1) {
                Order order = orderService.findOne(fukuan.getOrderId());
                if (order == null) {
                    return Result.error(4001, "创建失败");
                }
                fukuan.setOrderId(morderId);
                if (order.getMainAccounting() != fukuan.getCapitalId()) {
                    if (fukuan.getJiekuan() != null) {
                        int rtn = fukuanService.create(fukuan );
                        if (rtn != 1) {
                            return Result.error(4001, "创建失败");
                        }
                        return Result.ok(fukuan);
                    } else {
                        return Result.error(4001, "参数不匹配");
                    }
                } else {

                    int rtn = fukuanService.create(fukuan);
                    if (rtn != 1) {
                        logger.error("创建失败: {}", fukuan);
                        return Result.error(4001, "创建失败");
                    }
                    return Result.ok(fukuan);
                }
            } else {
                return Result.error(4001, "付款总金额不能大于采购货款总额");
            }
        } else {
            return Result.error(4001, "创建失败,采购货款总额为空");
        }


    }

    /**
     * 删除付款
     *
     * @return
     */
    @PutMapping("/{morderId}/fukuans/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id,
            @RequestBody @Validated(UpdateGroup.class) Fukuan fukuan
    ) {
        assert (fukuan.getOrderId() == morderId);
        fukuan.setId(id);
        int rtn = fukuanService.update(fukuan);
        if (rtn != 1) {
            return Result.error(4001, "更新失败", HttpStatus.BAD_REQUEST);
        }
        return Result.ok(1);
    }

    /**
     * 删除付款
     *
     * @return
     */
    @DeleteMapping("/{morderId}/fukuans/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        int rtn = fukuanService.delete(morderId, id);
        if (rtn != 1) {
            return Result.error(4001, "更新删除", HttpStatus.BAD_REQUEST);
        }
        return Result.ok(1);
    }


}
