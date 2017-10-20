package com.yimei.hs.same.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.same.dto.PageSettleBuyerDTO;
import com.yimei.hs.same.dto.PageSettleSellerDTO;
import com.yimei.hs.same.entity.SettleBuyer;
import com.yimei.hs.same.entity.SettleSeller;
import com.yimei.hs.same.service.SettleBuyerService;
import com.yimei.hs.same.service.SettleSellerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * Created by hary on 2017/9/21.
 */
@RestController
@RequestMapping("/api/business/{businessType}")
@Logined
public class SettleBuyerController {

    private static final Logger logger = LoggerFactory.getLogger(SettleBuyerController.class);


    @Autowired
    private SettleBuyerService settleBuyeService;

    private boolean isValidReq(String pos, BusinessType businessType) {
        return (
                businessType.equals(BusinessType.ying) && pos.equals("downstream")
                        ||
                        (businessType.equals(BusinessType.cang) && pos.equals("upstream"))
        );
    }

    /**
     * 获取所有下游结算
     *
     * @param pos pos-->downstream应收下游结算  upstream 仓押下游结算
     * @return
     */
    @GetMapping("/{morderId}/settlebuyer{pos}")
    public ResponseEntity<Result<Page<SettleBuyer>>> listYingDownstream(
            @PathVariable("pos") String pos,
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            PageSettleBuyerDTO pageSettleBuyerDTO
    ) {
        pageSettleBuyerDTO.setOrderId(morderId);

        if (isValidReq(pos, businessType)) {
            return Result.ok(settleBuyeService.getPage(pageSettleBuyerDTO));
        }

        return Result.error(4001, "invalid request");
    }


    /**
     * 获取下游结算
     *
     * @param id
     * @return
     */
    @GetMapping("/{morderId}/settlebuyer{pos}/{id}")
    public ResponseEntity<Result<SettleBuyer>> read(
            @PathVariable("pos") String pos,
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {

        if (isValidReq(pos, businessType)) {

            SettleBuyer settleDownstream = settleBuyeService.findOne(id);
            if (settleDownstream == null) {
                return Result.error(4001, "记录不存在", HttpStatus.BAD_REQUEST);
            } else {
                return Result.ok(settleDownstream);
            }
        }
        return Result.error(4001, "invalid request");
    }

    /**
     * 创建下游结算
     *
     * @return
     */
    @PostMapping("/{morderId}/settlebuyer{pos}")
    public ResponseEntity<Result<SettleBuyer>> create(
            @PathVariable("pos") String pos,
            @PathVariable("businessType") BusinessType businessType,
            @RequestBody @Validated(CreateGroup.class) SettleBuyer settleBuyer
    ) {
        if (isValidReq(pos, businessType)) {

            settleBuyeService.create(settleBuyer);
            return Result.ok(settleBuyer);
        }
        return Result.error(4001, "invalid request");
    }

    /**
     * 更新下游结算
     *
     * @return
     */
    @PutMapping("/{morderId}/settlebuyer{pos}/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("pos") String pos,
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id,
            @RequestBody @Validated(UpdateGroup.class) SettleBuyer settleBuyer
    ) {
        if (isValidReq(pos, businessType)) {
            assert (settleBuyer.getOrderId() == morderId);
            int rtn = settleBuyeService.update(settleBuyer);
            if (rtn != 1) {
                logger.error("更新失败: {}", settleBuyer);
                return Result.error(4001, "更新失败", HttpStatus.BAD_REQUEST);
            }

            return Result.ok(1);
        }
        return Result.error(4001, "invalid request");
    }

    /**
     * 删除下游结算
     *
     * @return
     */
    @DeleteMapping("/{morderId}/settlebuyer{pos}/{id}")
    public ResponseEntity<Result<Integer>> deletes(
            @PathVariable("pos") String pos,
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        if (isValidReq(pos, businessType)) {
            int rtn = settleBuyeService.delete(id);
            if (rtn != 1) {
                return Result.error(4001, "删除失败", HttpStatus.BAD_REQUEST);
            }

            return Result.ok(1);
        }
        return Result.error(4001, "invalid request");
    }

}
