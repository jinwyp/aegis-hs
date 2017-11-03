package com.yimei.hs.same.controller;

import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.same.dto.PageSettleTrafficDTO;
import com.yimei.hs.same.entity.SettleTraffic;
import com.yimei.hs.same.service.SettleTrafficService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/21.
 */

/**
 * Created by hary on 2017/9/21.
 */
@RestController
@RequestMapping("/api/business/{businessType}")
@Logined
public class SettleTrafficController {

    private static final Logger logger = LoggerFactory.getLogger(SettleTrafficController.class);

    @Autowired
    private SettleTrafficService settleService;

    /**
     * 获取所有huikuan
     *
     * @return
     */
    @GetMapping("/{morderId}/settletraffic")
    public ResponseEntity<Result<Page<SettleTraffic>>> list(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") long morderId,
            PageSettleTrafficDTO pageSettleTrafficDTO
    ) {

        pageSettleTrafficDTO.setOrderId(morderId);
        return Result.ok(settleService.getPageTraffic(pageSettleTrafficDTO));
    }

    /**
     * 获取huikuan
     *
     * @param id
     * @return
     */
    @GetMapping("/{morderId}/settletraffic/{id}")
    public ResponseEntity<Result<SettleTraffic>> read(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        SettleTraffic settleTraffic = settleService.findTraffic(id);
        if (settleTraffic == null) {
            return Result.error(4001, "记录不存在", HttpStatus.BAD_REQUEST);
        } else {
            return Result.ok(settleTraffic);
        }
    }

    /**
     * 创建huikuan
     *
     * @return
     */
    @PostMapping("/{morderId}/settletraffic")
    public ResponseEntity<Result<SettleTraffic>> create(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @RequestBody SettleTraffic settleTraffic) {
        settleTraffic.setOrderId(morderId);

        int rtn = settleService.create(settleTraffic);
        if (rtn != 1) {
            logger.error("创建失败: {}", settleTraffic);
            return Result.error(4001, "创建失败");
        }
        return Result.ok(settleTraffic);
    }

    /**
     * 更新huikuan
     *
     * @return
     */
    @PutMapping("/{morderId}/settletraffic/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id,
            @RequestBody @Validated(UpdateGroup.class) SettleTraffic settleTraffic
    ) {
        assert (morderId == settleTraffic.getOrderId());
        int rtn = settleService.update(settleTraffic);
        if (rtn != 1) {
            return Result.error(4001, "更新失败", HttpStatus.BAD_REQUEST);
        }
        return Result.ok(1);
    }

    /**
     * 逻辑删除
     *
     * @return
     */
    @DeleteMapping("/{morderId}/settletraffic/{id}")
    public ResponseEntity<Result<Integer>> delete(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        int rtn = settleService.delete(id);
        if (rtn != 1) {
            return Result.error(4001, "删除失败", HttpStatus.BAD_REQUEST);
        }
        return Result.ok(1);
    }

}
