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
    @GetMapping("/{morderId}/huankuans")
    public ResponseEntity<PageResult<YingHuankuan>> list(
            @PathVariable("morderId") Long morderId,
            PageYingHuankuanDTO pageYingHuankuanDTO) {

//        pageYingHuankuanDTO.setOrderId(morderId);
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
    @Transactional(readOnly =  false)
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
     * @return
     */
    @Transactional(readOnly =  false)
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
