package com.yimei.hs.same.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.cang.dto.CangAnalysisData;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.same.dto.PageSettleSellerDTO;
import com.yimei.hs.same.entity.SettleSeller;
import com.yimei.hs.same.service.DataAnalysisService;
import com.yimei.hs.same.service.SettleSellerService;
import com.yimei.hs.ying.entity.YingAnalysisData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * Created by hary on 2017/9/21.
 */
@RestController
@RequestMapping("/api/business/{businessType}")
@Logined
public class SettleSellerController {

    private static final Logger logger = LoggerFactory.getLogger(SettleSellerController.class);

    @Autowired
    private SettleSellerService settleSellerService;

    @Autowired
    private DataAnalysisService dataAnalysisService;

    private boolean isValidReq(String pos, BusinessType businessType) {
        return (businessType.equals(BusinessType.ying) && pos.equals("upstream"))
                || (businessType.equals(BusinessType.cang) && pos.equals("downstream")
        );
    }

    /**
     * 获取上游结算 - 分页
     *
     * @return
     */
    @GetMapping("/{morderId}/settleseller{pos}")
    public ResponseEntity<Result<Page<SettleSeller>>> listYingUpstream(
            @PathVariable("pos") String pos,
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            PageSettleSellerDTO pageSettleSellerDTO) {

        pageSettleSellerDTO.setOrderId(morderId);
        if (isValidReq(pos, businessType)) {
            return Result.ok(settleSellerService.getPage(pageSettleSellerDTO));
        }
        return Result.error(4001, "invalid request");
    }


    /**
     * 获取上游结算
     *
     * @param id
     * @return
     */
    @GetMapping("/{morderId}/settleseller{pos}/{id}")
    public ResponseEntity<Result<SettleSeller>> read(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("pos") String pos,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {

        if (isValidReq(pos, businessType)) {
            SettleSeller settleUpstream = settleSellerService.findOne(id);
            if (settleUpstream == null) {
                return Result.error(4001, "记录不存在", HttpStatus.BAD_REQUEST);
            } else {
                return Result.ok(settleUpstream);
            }
        }
        return Result.error(4001, "invalid request");
    }

    /**
     * 创建上游结算
     *
     * @return
     */

    @PostMapping("/{morderId}/settleseller{pos}")
    public ResponseEntity<Result<SettleSeller>> create(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("pos") String pos,
            @RequestBody @Validated(CreateGroup.class) SettleSeller settleSeller) {
        settleSeller.setOrderId(morderId);
        if (isValidReq(pos, businessType)) {

            BigDecimal totalHuikuanPaymentMoney = new BigDecimal("0");
            BigDecimal totalPaymentAmount = new BigDecimal("0");

            if (businessType.equals(BusinessType.ying)) {
                YingAnalysisData yingAnalysisData = dataAnalysisService.findOneYing(morderId, settleSeller.getHsId());
                totalHuikuanPaymentMoney = totalHuikuanPaymentMoney.add((yingAnalysisData.getTotalHuikuanPaymentMoney()==null?new BigDecimal("0"):yingAnalysisData.getTotalHuikuanPaymentMoney()));
                totalPaymentAmount = totalPaymentAmount.add((yingAnalysisData.getTotalPaymentAmount() == null ? new BigDecimal("0") : yingAnalysisData.getTotalPaymentAmount())
                        .subtract((yingAnalysisData.getTotalTradeGapFee() == null ? new BigDecimal("0") : yingAnalysisData.getTotalTradeGapFee())
                        ));
            } else if (businessType.equals(BusinessType.cang)) {
                CangAnalysisData cangAnalysisData = dataAnalysisService.findOneCang(morderId, settleSeller.getHsId());
                totalHuikuanPaymentMoney = totalHuikuanPaymentMoney.add((cangAnalysisData.getTotalHuikuanPaymentMoney()==null?new BigDecimal("0"):cangAnalysisData.getTotalHuikuanPaymentMoney()));
                totalPaymentAmount = totalPaymentAmount.add((cangAnalysisData.getPurchaseCargoAmountOfMoney() == null ? new BigDecimal("0") : cangAnalysisData.getPurchaseCargoAmountOfMoney())
                        .subtract((cangAnalysisData.getTotalTradeGapFee() == null ? new BigDecimal("0") : cangAnalysisData.getTotalTradeGapFee())
                        ));
            }

            boolean exit = settleSellerService.selectHsAndOrderId(morderId, settleSeller.getHsId());
            if (exit) {
                return Result.error(4001, "记录已存在");
            } else {
                 //    上游结算的限制条件：汇总回款总额 > 除贸易差价外的付款总额；
                if (totalHuikuanPaymentMoney.compareTo(totalPaymentAmount) == 1) {

                    int rtn = settleSellerService.create(settleSeller);
                    if (rtn != 1) {
                        logger.error("创建失败: {}", settleSeller);
                        return Result.error(4001, "创建失败");
                    }
                    return Result.ok(settleSeller);
                } else {
                    return Result.error(4001, "汇总回款总额要大于除贸易差价外的付款总额");
                }
            }

        }
        return Result.error(4001, "invalid request");
    }

    /**
     * 更新上游结算
     *
     * @return
     */
    @PutMapping("/{morderId}/settleseller{pos}/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("pos") String pos,
            @PathVariable("id") long id,
            @RequestBody @Validated(UpdateGroup.class) SettleSeller settleSeller
    ) {
//        assert (orderId == settleSeller.getOrderId());
        if (isValidReq(pos, businessType)) {
            settleSeller.setId(id);
            int rtn = settleSellerService.update(settleSeller);
            if (rtn != 1) {
                logger.error("更新失败: {}", settleSeller);
                return Result.error(4001, "更新失败", HttpStatus.BAD_REQUEST);
            }
            return Result.ok(1);
        }
        return Result.error(4001, "invalid request");
    }

    /**
     * 删除上游结算
     *
     * @return
     */
    @DeleteMapping("/{morderId}/settleseller{pos}/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("pos") String pos,
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        if (isValidReq(pos, businessType)) {
            int rtn = settleSellerService.delete(id);
            if (rtn != 1) {
                logger.error("删除失败: {}", id);
                return Result.error(4001, "更新失败", HttpStatus.BAD_REQUEST);
            }
            return Result.ok(1);
        }
        return Result.error(4001, "invalid request");
    }

}
