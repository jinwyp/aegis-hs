package com.yimei.hs.ying.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.enums.TrafficMode;
import com.yimei.hs.same.service.SettleSellerService;
import com.yimei.hs.ying.dto.PageYingFayunDTO;
import com.yimei.hs.ying.entity.YingFayun;
import com.yimei.hs.ying.service.YingFayunService;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api/business/{businessType}")
@RestController
@Logined
public class YingFayunController {

    private static final Logger logger = LoggerFactory.getLogger(YingFayunController.class);

    @Autowired
    YingFayunService yingFayunService;

    @Autowired
    SettleSellerService settleSellerService;
    /**
     * 获取所有fayun
     *
     * @return
     */
    @GetMapping("/{morderId}/fayuns")
    public ResponseEntity<Result<Page<YingFayun>>> list(
            @PathVariable("morderId") Long morderId,
            @PathVariable("businessType") BusinessType businessType,
            PageYingFayunDTO pageYingFayunDTO) {
        pageYingFayunDTO.setOrderId(morderId);
        Page<YingFayun> page = yingFayunService.getPage(pageYingFayunDTO);
        return Result.ok(page);
    }

    /**
     * 获取fayun
     *
     * @param id
     * @return
     */
    @GetMapping("/{morderId}/fayuns/{id}")
    public ResponseEntity<Result<YingFayun>> read(
            @PathVariable("morderId") Long morderId,
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("id") long id) {

        YingFayun fayun = yingFayunService.findOne(id);
        if (fayun == null) {
            return Result.error(4001, "记录不存在", HttpStatus.BAD_REQUEST);
        } else {
            return Result.ok(fayun);
        }
    }

    /**
     * 创建fayun
     *
     * @return
     */
    @PostMapping("/{morderId}/fayuns")
    public ResponseEntity<Result<YingFayun>> create(
            @PathVariable("morderId") Long morderId,
            @PathVariable("businessType") BusinessType businessType,
            @RequestBody @Validated(CreateGroup.class) YingFayun yingFayun
    ) {

        if (yingFayun.getUpstreamTrafficMode().equals(TrafficMode.MOTOR)) {
            if (yingFayun.getUpstreamCars() == null
                    || yingFayun.getUpstreamCars() <= 0
                    ) {
                return Result.error(4001, "上游汽运数量不匹配", HttpStatus.BAD_REQUEST);
            }
        }

        if (yingFayun.getUpstreamTrafficMode().equals(TrafficMode.SHIP)) {
            if (StringUtils.isEmpty(yingFayun.getUpstreamShip())) {
                return Result.error(4001, "上游船运名称不能为空", HttpStatus.BAD_REQUEST);
            }
        }

        if (yingFayun.getUpstreamTrafficMode().equals(TrafficMode.RAIL)) {
            if (StringUtils.isEmpty(yingFayun.getUpstreamJHH())) {
                return Result.error(4001, "上游火运计划号不能为空", HttpStatus.BAD_REQUEST);
            }
        }

        if (yingFayun.getDownstreamTrafficMode().equals(TrafficMode.MOTOR)) {
            if (yingFayun.getDownstreamCars() == null
                    || yingFayun.getDownstreamCars() <= 0
                    ) {
                return Result.error(4001, "下游汽运数量不匹配", HttpStatus.BAD_REQUEST);
            }
        }

        if (yingFayun.getDownstreamTrafficMode().equals(TrafficMode.SHIP)) {
            if (StringUtils.isEmpty(yingFayun.getDownstreamShip())) {
                return Result.error(4001, "下游船运名称不能为空", HttpStatus.BAD_REQUEST);
            }
        }

        if (yingFayun.getDownstreamTrafficMode().equals(TrafficMode.RAIL)) {
            if (StringUtils.isEmpty(yingFayun.getDownstreamJHH())) {
                return Result.error(4001, "下游火运计划号不能为空", HttpStatus.BAD_REQUEST);
            }
        }
        yingFayun.setOrderId(morderId);
        int rtn;
        if (!settleSellerService.selectHsAndOrderId(morderId, yingFayun.getHsId())) {

            try {
                rtn = yingFayunService.create(yingFayun);
            } catch (Exception e) {
                return Result.error(4001, "创建失败", HttpStatus.BAD_REQUEST);
            }
            if (rtn != 1) {
                return Result.error(4001, "创建失败", HttpStatus.BAD_REQUEST);
            }
            return Result.ok(yingFayun);
        } else {
            return Result.error(4001,"本核算月结算已经完成，不能添加发运",HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 更新fayun
     *
     * @return
     */
    @PutMapping("/{morderId}/fayuns/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") Long id,
            @PathVariable("businessType") BusinessType businessType,
            @RequestBody @Validated(UpdateGroup.class) YingFayun yingFayun) {
        yingFayun.setId(id);
        int cnt = yingFayunService.update(yingFayun);
        if (cnt != 1) {
            return Result.error(4001, "更新失败", HttpStatus.BAD_REQUEST);
        }
        return Result.ok(1);
    }

    /**
     * 逻辑删除
     *
     * @param morderId
     * @param id
     * @return
     */
    @DeleteMapping("/{morderId}/fayuns/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("morderId") Long morderId,
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("id") Long id
    ) {
        int cnt = yingFayunService.delete(id,morderId);
        if (cnt != 1) {
            return Result.error(4001, "删除失败", HttpStatus.BAD_REQUEST);
        }
        return Result.ok(1);
    }


    // 发运统计
    @Data
    private static class FayunStat {
    }

    /**
     * 发运统计
     */
    @GetMapping("/{morderId}/fayuns-stat")
    public ResponseEntity<Result<FayunStat>> stat(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId
    ) {
        return Result.ok(null);
    }

}
