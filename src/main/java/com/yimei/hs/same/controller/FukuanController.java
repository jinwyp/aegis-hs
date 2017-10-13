package com.yimei.hs.same.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.same.dto.PageFukuanDTO;
import com.yimei.hs.same.entity.Fukuan;
import com.yimei.hs.same.entity.Order;
import com.yimei.hs.same.service.FukuanService;
import com.yimei.hs.same.service.JiekuanService;
import com.yimei.hs.same.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api/business/{businessType}")
@RestController
@Logined
public class FukuanController {

    private static final Logger logger = LoggerFactory.getLogger(FukuanController.class);


    @Autowired
    private FukuanService fukuanService;

    @Autowired
    private OrderService orderService;

    /**
     * 获取付款-分页
     *
     * @return
     */
    @GetMapping("/{morderId}/fukuans")
    public ResponseEntity<Result<Page<Fukuan>>> list(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            PageFukuanDTO pageFukuanDTO) {

        pageFukuanDTO.setOrderId(morderId);
        Page<Fukuan> page = fukuanService.getPage(pageFukuanDTO);

        return Result.ok(page);
    }

    /**
     * @param businessType
     * @param morderId
     * @return
     */
    @GetMapping("/{morderId}/fukuansUnfinished")
    public ResponseEntity<Result<List<Fukuan>>> list(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId) {
        return Result.ok(fukuanService.getListUnfinished(morderId));
    }


    /**
     * 获取付款
     *
     * @param id
     * @return
     */
    @GetMapping("/{morderId}/fukuans/{id}")
    public ResponseEntity<Result<Fukuan>> read(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        Fukuan fukuan = fukuanService.findOne(id);
        if (fukuan == null) {
            return Result.error(4001, "记录不存在", HttpStatus.NOT_FOUND);
        } else {
            return Result.ok(fukuan);
        }
    }

    /**
     * 创建付款
     *
     * @return
     */
    @PostMapping("/{morderId}/fukuans")
    public ResponseEntity<Result<Fukuan>> create(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @RequestBody @Validated(CreateGroup.class) Fukuan fukuan
    ) {
        Order order = orderService.findOne(fukuan.getOrderId());
        if (order == null) {
            return Result.error(4001, "创建失败");
        }
        if (order.getMainAccounting() == fukuan.getCapitalId()) {
            if (fukuan.getJiekuan() != null) {
                fukuan.setOrderId(morderId);
                int rtn = fukuanService.create(fukuan);
                if (rtn != 1) {
                    logger.error("创建失败: {}", fukuan);
                    return Result.error(4001, "创建失败");
                }
                return Result.ok(fukuan);
            } else {
                return Result.error(4001, "参数不匹配");
            }
        } else {
            fukuan.setOrderId(morderId);
            int rtn = fukuanService.create(fukuan);
            if (rtn != 1) {
                logger.error("创建失败: {}", fukuan);
                return Result.error(4001, "创建失败");
            }
            return Result.ok(fukuan);
        }

    }

    /**
     * 删除付款
     *
     * @return
     */
    @PutMapping("/{morderId}/fukuans/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id,
            @RequestBody @Validated(UpdateGroup.class) Fukuan fukuan
    ) {
        assert (fukuan.getOrderId() == morderId);
        fukuan.setId(id);
        int rtn = fukuanService.update(fukuan);
        if (rtn != 1) {
            return Result.error(4001, "更新失败", HttpStatus.NOT_FOUND);
        }
        return Result.ok(1);
    }

    /**
     * 删除付款
     *
     * @return
     */
    @DeleteMapping("/{morderId}/fukuans/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("businessType") BusinessType businessType,
            @PathVariable("morderId") Long morderId,
            @PathVariable("id") long id
    ) {
        int rtn = fukuanService.delete(morderId, id);
        if (rtn != 1) {
            return Result.error(4001, "更新失败", HttpStatus.NOT_FOUND);
        }
        return Result.ok(1);
    }


}
