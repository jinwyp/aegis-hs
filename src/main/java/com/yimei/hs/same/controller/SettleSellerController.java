package com.yimei.hs.same.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.dto.PageSettleSellerDTO;
import com.yimei.hs.same.entity.SettleSeller;
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
@RequestMapping("/api/ying")
@Logined
public class SettleSellerController {

    private static final Logger logger = LoggerFactory.getLogger(SettleSellerController.class);

    @Autowired
    private SettleSellerService settleSellerService;

    /**
     * 获取上游结算 - 分页
     *
     * @return
     */
    @GetMapping("/{morderId}/settleupstream")
    public ResponseEntity<Result<Page<SettleSeller>>> list(
            @PathVariable("morderId") Long morderId,
            PageSettleSellerDTO pageSettleSellerDTO) {

        pageSettleSellerDTO.setOrderId(morderId);
        return Result.ok(settleSellerService.getPageUpstream(pageSettleSellerDTO));
    }

    /**
     * 获取上游结算
     *
     * @param id
     * @return
     */
    @GetMapping("/{morderId}/settleupstream/{id}")
    public ResponseEntity<Result<SettleSeller>> read(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {

        SettleSeller settleUpstream = settleSellerService.findUpstream(id);
        if (settleUpstream == null) {
            return Result.error(4001, "记录不存在", HttpStatus.NOT_FOUND);
        } else {
            return Result.ok(settleUpstream);
        }
    }

    /**
     * 创建上游结算
     *
     * @return
     */

    @PostMapping("/{morderId}/settleupstream")
    public ResponseEntity<Result<SettleSeller>> create(
            @PathVariable("morderId") Long morderId,
            @RequestBody @Validated(CreateGroup.class) SettleSeller yingSettleUpstream) {
        yingSettleUpstream.setOrderId(morderId);
        int rtn = settleSellerService.createUpstream(yingSettleUpstream);
        if (rtn != 1) {
            logger.error("创建失败: {}", yingSettleUpstream);
            return Result.error(4001, "创建失败");
        }
        return Result.ok(yingSettleUpstream);
    }

    /**
     * 更新上游结算
     *
     * @return
     */
    @PutMapping("/{morderId}/settleupstream/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id,
            @RequestBody @Validated(UpdateGroup.class) SettleSeller yingSettleUpstream
    ) {
//        assert (orderId == yingSettleUpstream.getOrderId());
        yingSettleUpstream.setId(id);
        int rtn = settleSellerService.updateUpstream(yingSettleUpstream);
        if (rtn != 1) {
            logger.error("更新失败: {}", yingSettleUpstream);
            return Result.error(4001, "更新失败", HttpStatus.NOT_FOUND);
        }
        return Result.ok(1);
    }

    /**
     * 删除上游结算
     *
     * @return
     */
    @DeleteMapping("/{morderId}/settleupstream/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        int rtn = settleSellerService.deleteUpstream(id);
        if (rtn != 1) {
            logger.error("删除失败: {}", id);
            return Result.error(4001, "更新失败", HttpStatus.NOT_FOUND);
        }
        return Result.ok(1);
    }

}
