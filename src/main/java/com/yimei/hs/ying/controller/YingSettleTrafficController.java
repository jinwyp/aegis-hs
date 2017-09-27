package com.yimei.hs.ying.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.ying.entity.YingSettleTraffic;
import com.yimei.hs.boot.api.PageResult;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.ying.dto.PageYingSettleTrafficDTO;
import com.yimei.hs.ying.service.YingSettleService;
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
@RequestMapping("/api/ying")
@Logined
public class YingSettleTrafficController {

    private static final Logger logger = LoggerFactory.getLogger(YingSettleTrafficController.class);

    @Autowired
    private YingSettleService yingSettleService;

    /**
     * 获取所有huikuan
     *
     * @return
     */
    @GetMapping("/{morderId}/settletraffic")
    public ResponseEntity<PageResult<YingSettleTraffic>> list( @PathVariable("morderId") long morderId,  PageYingSettleTrafficDTO pageYingSettleTrafficDTO) {

        pageYingSettleTrafficDTO.setOrderId(morderId);
        return PageResult.ok(yingSettleService.getPageTraffic(pageYingSettleTrafficDTO));
    }

    /**
     * 获取huikuan
     *
     * @param id
     * @return
     */
    @GetMapping("/{morderId}/settletraffic/{id}")
    public ResponseEntity<Result<YingSettleTraffic>> read(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        YingSettleTraffic settleTraffic = yingSettleService.findTraffic(id);
        if (settleTraffic == null) {
            return Result.error(4001, "记录不存在", HttpStatus.NOT_FOUND);
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
    @Transactional(readOnly = false)
    public ResponseEntity<Result<YingSettleTraffic>> create( @PathVariable("morderId") Long morderId,  @RequestBody YingSettleTraffic yingSettleTraffic) {
        yingSettleTraffic.setOrderId(morderId);

        yingSettleService.createTraffic(yingSettleTraffic);
        return Result.ok(yingSettleTraffic);
    }

    /**
     * 更新huikuan
     *
     * @return
     */
    @Transactional(readOnly = false)
    @PutMapping("/{morderId}/settletraffic/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id,
            @RequestBody @Validated(UpdateGroup.class) YingSettleTraffic yingSettleTraffic
    ) {
        assert (morderId == yingSettleTraffic.getOrderId());
        int rtn = yingSettleService.udpateTraffic(yingSettleTraffic);
        if (rtn != 1) {
            return Result.error(4001, "更新失败", HttpStatus.NOT_FOUND);
        }
        return Result.ok(1);
    }
}
