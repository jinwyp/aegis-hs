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
import java.util.List;

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
            return Result.error(4001, "记录不存在", HttpStatus.NOT_FOUND);
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
            @RequestBody @Validated(CreateGroup.class) Huankuan huankuan
    ) {
        // 1. 找出当前订单借款记录 - 还款尚未对应完成的记录
//        List<Jiekuan> jiekuans = jiekuanService.huankuanUnfinished(huankuan.getOrderId());
        List<HuankuanMap> huankuanMaps = huankuan.getHuankuanMapList();

        for (HuankuanMap huankuanMap : huankuanMaps) {
            Jiekuan jiekuanDb= jiekuanService.findOne(huankuanMap.getOrderId());
            if (jiekuanDb != null) {
                if (huankuanMap.getPrincipal().compareTo(jiekuanDb.getAmount()) ==1 ) {
                    return Result.error(4001, "创建失败");
                }
            } else {
                return Result.error(4001, "创建失败");
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
        //
        huankuan.setId(id);
        assert (morderId == huankuan.getOrderId());


        // 2删除还款记录详情
        huankuanMapMapper.deleteByPrimaryKey(huankuan.getId());
        int cnt = huankuanService.update(huankuan);
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
            return Result.error(4001, "删除失败", HttpStatus.NOT_FOUND);
        }
        return Result.ok(1);
    }
}
