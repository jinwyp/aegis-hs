package com.yimei.hs.same.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.same.dto.PageHuankuanDTO;
import com.yimei.hs.same.entity.*;
import com.yimei.hs.same.mapper.HuankuanMapMapper;
import com.yimei.hs.same.service.JiekuanService;
import com.yimei.hs.same.service.HuankuanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * Created by hary on 2017/9/22.
 */
@RequestMapping("/api/business/{businessType}")
@RestController
@Logined
public class HuankuanController {

    private static final Logger logger = LoggerFactory.getLogger(HuankuanController.class);

    @Autowired
    private HuankuanService huankuanService;

    @Autowired
    private JiekuanService jiekuanService;

    @Autowired
    private HuankuanMapMapper huankuanMapMapper;

    /**
     * 获取所有huikuan
     *
     * @return
     */
    @GetMapping("/{morderId}/huankuans")
    public ResponseEntity<Result<Page<Huankuan>>> list(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            PageHuankuanDTO pageHuankuanDTO) {
        pageHuankuanDTO.setOrderId(morderId);
        return Result.ok(huankuanService.getPage(pageHuankuanDTO));
    }

    /**
     * 获取huikuan
     *
     * @param id
     * @return
     */
    @GetMapping("/{morderId}/huankuans/{id}")
    public ResponseEntity<Result<Huankuan>> read(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        Huankuan huankuan = huankuanService.findOne(id);
        if (huankuan == null) {
            return Result.error(4001, "记录不存在", HttpStatus.BAD_REQUEST);
        } else {
            return Result.ok(huankuan);
        }

    }

    /**
     * 创建还款, 还款需要手动对应到借款
     *
     * @return
     */
    @PostMapping("/{morderId}/huankuans")
    public ResponseEntity<Result<Huankuan>> create(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @RequestBody @Validated(CreateGroup.class) Huankuan huankuan
    ) {
        assert (morderId == huankuan.getOrderId());
        // 1. 找出当前订单借款记录 - 还款尚未对应完成的记录
        List<Jiekuan> jiekuans = jiekuanService.huankuanUnfinished(huankuan.getOrderId());
        List<HuankuanMap> huankuanMaps = huankuan.getHuankuanMapList();

       // 还款总额
        Map<Long, BigDecimal> huankanedAmount = new HashMap<Long, BigDecimal>();


        if (huankuanMaps == null
                || huankuanMaps.size() == 0) {
            return Result.error(4001, "借款不能为空");
        } else if (huankuanMaps!=null) {

            for (HuankuanMap huankuanMap : huankuanMaps) {
                if (huankuanMap.getJiekuanId() == null) {
                    return Result.error(4001, "借款款id不能为空");
                }
            }

            Map<Long, List<HuankuanMap>> huankuanmaps = huankuanMaps.stream().collect(groupingBy(HuankuanMap::getJiekuanId));

            for (Long id:huankuanmaps.keySet()) {
                huankanedAmount.put(id, huankuanmaps.get(id).stream().map(HuankuanMap::getPrincipal).reduce(BigDecimal.ZERO, BigDecimal::add));
            }
        }
        for (HuankuanMap huankuanMap : huankuanMaps) {

            if (huankuanMap.getJiekuanId() == null) {
                return Result.error(4001, "借款款id不能为空");
            }


            Jiekuan jiekuanDb = jiekuanService.findOne(huankuanMap.getJiekuanId());


            if (jiekuanDb != null) {

                BigDecimal totalHuankuaned =jiekuanDb.getHuankuanMapList().stream().map(HuankuanMap::getPrincipal).reduce(BigDecimal.ZERO, BigDecimal::add);

                if (huankanedAmount.get(jiekuanDb.getId()).compareTo(

                        jiekuanDb.getAmount().subtract(totalHuankuaned)

                ) == 1) {
                    return Result.error(4001, "还款本金总和大于借款本金");
                }

            } else {
                return Result.error(4001, "该笔借款不存在");
            }

        }



        // 2. 创建还款
        int rtn = huankuanService.create(huankuan);
        if (rtn != 1) {
            logger.error("创建失败: {}", huankuan);
            return Result.error(4001, "创建失败");
        }
        return Result.ok(huankuan);

    }

    /**
     * 更新huankuan
     *
     * @return
     */
    @PutMapping("/{morderId}/huankuans/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id,
            @RequestBody @Validated(UpdateGroup.class) Huankuan huankuan
    ) {

        assert (morderId == huankuan.getOrderId());
        huankuan.setId(id);
        // 2删除还款记录详情
        huankuanMapMapper.deleteByPrimaryKey(huankuan.getId());
        int cnt = huankuanService.update(huankuan);
        if (cnt != 1) {
            return Result.error(4001, "更新失败", HttpStatus.BAD_REQUEST);
        }
        return Result.ok(1);
    }

    /**
     * 删除还款
     *
     * @return
     */
    @DeleteMapping("/{morderId}/huankuans/{id}")
    public ResponseEntity<Result<Integer>> delete(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        int cnt = huankuanService.delete(id);
        List<HuankuanMap> huankuanMaps = huankuanMapMapper.getListByHuankuanId(id);
        for (HuankuanMap huankuanMap : huankuanMaps) {
            huankuanMapMapper.deleteByPrimaryKey(huankuanMap.getId());
        }
        if (cnt != 1) {
            return Result.error(4001, "删除失败", HttpStatus.BAD_REQUEST);
        }
        return Result.ok(1);
    }


}
