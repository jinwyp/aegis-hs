package com.yimei.hs.ying.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.ying.dto.PageYingHuankuanDTO;
import com.yimei.hs.ying.entity.YingFukuan;
import com.yimei.hs.ying.entity.YingHuankuan;
import com.yimei.hs.ying.service.YingFukuanService;
import com.yimei.hs.ying.service.YingHuankuanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by hary on 2017/9/22.
 */
@RequestMapping("/api/ying")
@RestController
@Logined
public class YingHuankuanController {

    private static final Logger logger = LoggerFactory.getLogger(YingHuankuanController.class);

    @Autowired
    private YingHuankuanService yingHuankuanService;

    @Autowired
    private YingFukuanService yingFukuanService;

    /**
     * 获取所有huikuan
     *
     * @return
     */
    @GetMapping("/{morderId}/huankuans")
    public ResponseEntity<Result<Page<YingHuankuan>>> list(
            @PathVariable("morderId") Long morderId,
            PageYingHuankuanDTO pageYingHuankuanDTO) {
        pageYingHuankuanDTO.setOrderId(morderId);
        return Result.ok(yingHuankuanService.getPage(pageYingHuankuanDTO));
    }

    /**
     * 获取huikuan
     *
     * @param id
     * @return
     */
    @GetMapping("/{morderId}/huankuans/{id}")
    public ResponseEntity<Result<YingHuankuan>> read(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        YingHuankuan yingHuankuan = yingHuankuanService.findOne(id);
        if (yingHuankuan == null) {
            return Result.error(4001, "记录不存在", HttpStatus.NOT_FOUND);
        } else {
            return Result.ok(yingHuankuan);
        }

    }

    /**
     * 创建还款, 还款需要手动对应到付款
     *
     * @return
     */
    @PostMapping("/{morderId}/huankuans")
    public ResponseEntity<Result<YingHuankuan>> create(
            @RequestBody @Validated(CreateGroup.class) YingHuankuan yingHuankuan
    ) {

        // 1. 找出当前订单付款记录-还款尚未对应完成的记录j
        List<YingFukuan> fukuans = yingFukuanService.huankuanUnfinished(yingHuankuan.getOrderId());


        // 2. 校验 所有还款map明细的利息汇总校验
        BigDecimal inTotal = yingHuankuan.getHuankuanMapList().stream().map(m -> m.getInterest()).reduce(BigDecimal.ZERO, (a, b) -> a.add(b));

        if (inTotal.compareTo(yingHuankuan.getHuankuanInterest()) != 0
                ) {
            return Result.error(4001, "invalid request: 所有还款map明细的amount汇总校验");
        }

        // 3. 校验 所有还款map明细的本金汇总
        BigDecimal pTotal = yingHuankuan.getHuankuanMapList().stream().map(m -> m.getPrincipal()).reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
        if (pTotal.compareTo(yingHuankuan.getHuankuanPrincipal()) != 0
                ) {
            return Result.error(4001, "invalid request: 所有还款map明细的本金汇总");
        }

        int rtn = yingHuankuanService.create(yingHuankuan);
        if (rtn != 1) {
            logger.error("创建失败: {}", yingHuankuan);
            return Result.error(4001, "创建失败");
        }
        return Result.ok(yingHuankuan);
    }

    /**
     * 更新huikuan
     *
     * @return
     */
    @Transactional(readOnly = false)
    @PutMapping("/{morderId}/huankuans/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id,
            @RequestBody @Validated(UpdateGroup.class) YingHuankuan yingHuankuan
    ) {
        //
        yingHuankuan.setId(id);
        assert (morderId == yingHuankuan.getOrderId());
        int cnt = yingHuankuanService.update(yingHuankuan);
        if (cnt != 1) {
            return Result.error(4001, "更新失败", HttpStatus.NOT_FOUND);
        }
        return Result.ok(1);
    }

    /**
     * 删除还款
     *
     * @return
     */
    @Transactional(readOnly = false)
    @DeleteMapping("/{morderId}/huankuans/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        int cnt = yingHuankuanService.delete(id);
        if (cnt != 1) {
            return Result.error(4001, "删除失败", HttpStatus.NOT_FOUND);
        }
        return Result.ok(1);
    }
}
