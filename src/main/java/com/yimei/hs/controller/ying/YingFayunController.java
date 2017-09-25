package com.yimei.hs.controller.ying;

import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.entity.YingFayun;
import com.yimei.hs.boot.PageResult;
import com.yimei.hs.boot.Result;
import com.yimei.hs.entity.dto.ying.PageYingFayunDTO;
import com.yimei.hs.service.ying.YingFayunService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api/ying")
@RestController
public class YingFayunController {

    private static final Logger logger = LoggerFactory.getLogger(YingFayunController.class);


    @Autowired
    YingFayunService yingFayunService;

    /**
     * 获取所有fayun
     *
     * @return
     */
    @GetMapping("/{orderId}/fayuns")
    public ResponseEntity<PageResult<YingFayun>> list(PageYingFayunDTO pageYingFayunDTO) {
        Page<YingFayun> page = yingFayunService.getPage(pageYingFayunDTO);
        return PageResult.ok(page);
    }

    /**
     * 获取fayun
     *
     * @param id
     * @return
     */
    @GetMapping("/{orderId}fayuns/{id}")
    public ResponseEntity<Result<YingFayun>> read(
            @PathVariable("orderId") Long orderId,
            @PathVariable("id") long id) {

        YingFayun fayun = yingFayunService.findOne(id);
        if (fayun == null) {
            return Result.error(4001, "记录不存在");
        } else {
            return Result.ok(fayun);
        }
    }

    /**
     * 创建fayun
     *
     * @return
     */
    @PostMapping("/{orderId}/fayuns")
    public ResponseEntity<Result<YingFayun>> create(YingFayun yingFayun) {
        yingFayunService.create(yingFayun);
        return Result.ok(yingFayun);
    }

    /**
     * 更新fayun
     *
     * @return
     */
    @PutMapping("/{orderId}/fayuns/{id}")
    public ResponseEntity<Result<YingFayun>> update(YingFayun yingFayun) {
        yingFayunService.update(yingFayun);
        return Result.ok(yingFayun);
    }


    // 发运统计
    @Data
    private static class FayunStat {
    }

    /**
     *  发运统计
     */
    @GetMapping("/{orderId}/fayuns-stat")
    public ResponseEntity<Result<FayunStat>> stat(
            @PathVariable("orderId") long orderId
    ) {
        return Result.ok(null);
    }

}
