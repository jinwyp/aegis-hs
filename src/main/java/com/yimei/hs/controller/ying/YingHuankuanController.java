package com.yimei.hs.controller.ying;

import com.yimei.hs.entity.YingHuankuan;
import com.yimei.hs.entity.dto.PageResult;
import com.yimei.hs.entity.dto.Result;
import com.yimei.hs.entity.dto.ying.PageYingHuankuanDTO;
import com.yimei.hs.service.ying.YingHuankuanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/22.
 */
@RequestMapping("/api/ying")
@RestController
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
    public ResponseEntity<PageResult<YingHuankuan>> list(PageYingHuankuanDTO pageYingHuankuanDTO) {
        return PageResult.ok(yingHuankuanService.getPage(pageYingHuankuanDTO));
    }

    /**
     * 获取huikuan
     *
     * @param id
     * @return
     */
    @GetMapping("/{orderId}/huankuans/:id")
    public ResponseEntity<Result<YingHuankuan>> read(
            @PathVariable("orderId") long orderId,
            @PathVariable("id") long id
    ) {
        return Result.ok(yingHuankuanService.findOne(id));
    }

    /**
     * 创建huikuan
     *
     * @return
     */
    @PostMapping("/{orderId}/huankuans")
    public ResponseEntity<Result<YingHuankuan>> create(YingHuankuan yingHuankuan) {
        yingHuankuanService.create(yingHuankuan);
        return Result.ok(yingHuankuan);
    }

    /**
     * 更新huikuan
     *
     * @return
     */
    @PutMapping("/{orderId}/huankuans/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("orderId") long orderId,
            @PathVariable("id") long id
    ) {
        return Result.ok(1);
    }
}