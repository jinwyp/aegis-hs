package com.yimei.hs.ying.controller;

import com.yimei.hs.boot.api.CreateGroup;
import com.yimei.hs.boot.api.UpdateGroup;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.ying.entity.YingHuankuan;
import com.yimei.hs.boot.api.PageResult;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.ying.dto.PageYingHuankuanDTO;
import com.yimei.hs.ying.service.YingHuankuanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 获取所有huikuan
     *
     * @return
     */
    @GetMapping("/{orderId}/huankuans")
    public ResponseEntity<PageResult<YingHuankuan>> list(
            @PathVariable("orderId") Long orderId,
            PageYingHuankuanDTO pageYingHuankuanDTO) {

//        pageYingHuankuanDTO.setOrderId(orderId);
//        System.out.println("-------------------------------------------");
//        System.out.println(pageYingHuankuanDTO);
//        System.out.println("-------------------------------------------");

        return PageResult.ok(yingHuankuanService.getPage(pageYingHuankuanDTO));
    }

    /**
     * 获取huikuan
     *
     * @param id
     * @return
     */
    @GetMapping("/{orderId}/huankuans/{id}")
    public ResponseEntity<Result<YingHuankuan>> read(
            @PathVariable("orderId") long orderId,
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
     * 创建huikuan
     *
     * @return
     */
    @PostMapping("/{orderId}/huankuans")
    public ResponseEntity<Result<YingHuankuan>> create(
            @RequestBody @Validated(CreateGroup.class) YingHuankuan yingHuankuan
    ) {
        yingHuankuanService.create(yingHuankuan);
        return Result.ok(yingHuankuan);
    }

    /**
     * 更新huikuan
     *
     * @return
     */
    @Transactional(readOnly =  false)
    @PutMapping("/{orderId}/huankuans/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("orderId") long orderId,
            @PathVariable("id") long id,
            @RequestBody @Validated(UpdateGroup.class) YingHuankuan yingHuankuan
    ) {
        yingHuankuan.setId(id);
        assert (orderId == yingHuankuan.getOrderId());
        int cnt = yingHuankuanService.update(yingHuankuan);
        if (cnt != 1) {
            return Result.error(4001, "更新失败");
        }
        return Result.ok(1);
    }
}

