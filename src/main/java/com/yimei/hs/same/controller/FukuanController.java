package com.yimei.hs.same.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.cang.entity.CangAnalysisData;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.enums.PaymentPurpose;
import com.yimei.hs.same.dto.PageFukuanDTO;
import com.yimei.hs.same.entity.Fukuan;
import com.yimei.hs.same.entity.Order;
import com.yimei.hs.same.service.DataAnalysisService;
import com.yimei.hs.same.service.FukuanService;
import com.yimei.hs.same.service.OrderService;
import com.yimei.hs.same.service.SettleSellerService;
import com.yimei.hs.ying.entity.AnalysisData;
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

    @Autowired
    private SettleSellerService settleSellerService;

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
        totalPaymentMoney = totalPaymentMoney.add(dataAnalysisService.findTotalPaymentAmount(morderId, fukuan.getHsId()));
        if (businessType.equals(BusinessType.ying)) {
            purchaseCargoAmountofMoney = purchaseCargoAmountofMoney.add((dataAnalysisService.findPurchaseCargoAmountOfMoney(morderId, fukuan.getHsId(), BusinessType.ying)));
        } else if (businessType.equals(BusinessType.cang)) {
            AnalysisData cangAnalysisData = dataAnalysisService.findOneCang( fukuan.getHsId(),morderId);
            purchaseCargoAmountofMoney = purchaseCargoAmountofMoney.add((dataAnalysisService.findPurchaseCargoAmountOfMoney(morderId, fukuan.getHsId(), BusinessType.cang)));
        }

        //判断该核算月是否已经结算完成
        if(settleSellerService.selectHsAndOrderId(morderId, fukuan.getHsId())){

            //  上游结算完成后，可以添加付款，但添加的付款用途不能为“货款”或“运费”
            if (fukuan.getPayUsage().equals(PaymentPurpose.PAYMENT_FOR_GOODS) || fukuan.getPayUsage().equals(PaymentPurpose.FREIGNHT ) ||fukuan.getPayUsage().equals(PaymentPurpose.DEPOSITECASH)) {
                return Result.error(4001, "本月结算已经完成，不能录入货款与运费");
            }
        }
        if (purchaseCargoAmountofMoney != null) {
            // 付款的限制条件：付款总金额 < 当前计算出的采购货款总额
//            if (purchaseCargoAmountofMoney.compareTo(totalPaymentMoney) == 1) {
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
//            } else {
//                return Result.error(4001, "付款总金额不能大于采购货款总额");
//            }
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
    public ResponseEntity<Result<Integer>> deleted(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        int rtn = fukuanService.delete( id,morderId);
        if (rtn != 1) {
            return Result.error(4001, "更新删除", HttpStatus.BAD_REQUEST);
        }
        return Result.ok(1);
    }


}
