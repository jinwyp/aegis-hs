package com.yimei.hs.ying.controller;

import com.yimei.hs.ying.entity.YingSettleTraffic;
import com.yimei.hs.boot.PageResult;
import com.yimei.hs.boot.Result;
import com.yimei.hs.ying.dto.PageYingSettleTrafficDTO;
import com.yimei.hs.ying.service.YingSettleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/21.
 */

/**
 * Created by hary on 2017/9/21.
 */
@RestController
@RequestMapping("/api/ying")
public class YingSettleTrafficController {

    private static final Logger logger = LoggerFactory.getLogger(YingSettleTrafficController.class);

    @Autowired
    private YingSettleService yingSettleService;

    /**
     * 获取所有huikuan
     *
     * @return
     */
    @GetMapping("/{orderId}/settletraffic")
    public ResponseEntity<PageResult<YingSettleTraffic>> list(PageYingSettleTrafficDTO pageYingSettleTrafficDTO) {
        return PageResult.ok(yingSettleService.getPageTraffic(pageYingSettleTrafficDTO));
    }

    /**
     * 获取huikuan
     *
     * @param id
     * @return
     */
    @GetMapping("/{orderId}/settletraffic/{id}")
    public ResponseEntity<Result<YingSettleTraffic>> read(
            @PathVariable("orderId") long orderId,
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
    @PostMapping("/{orderId}/settletraffic")
    public ResponseEntity<Result<YingSettleTraffic>> create(YingSettleTraffic yingSettleTraffic) {
        yingSettleService.createTraffic(yingSettleTraffic);
        return Result.ok(yingSettleTraffic);
    }

    /**
     * 更新huikuan
     *
     * @return
     */
    @PutMapping("/{orderId}/settletraffic/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("orderId") long orderId,
            @PathVariable("id") long id,
            @RequestBody YingSettleTraffic yingSettleTraffic
    ) {
        assert (orderId == yingSettleTraffic.getOrderId());
        int rtn = yingSettleService.udpateTraffic(yingSettleTraffic);
        if (rtn != 1) {
            return Result.error(4001, "更新失败");
        }
        return Result.ok(1);
    }
}
