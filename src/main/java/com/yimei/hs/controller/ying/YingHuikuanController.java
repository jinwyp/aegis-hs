package com.yimei.hs.controller.ying;

import com.yimei.hs.entity.YingHuikuan;
import com.yimei.hs.entity.dto.PageResult;
import com.yimei.hs.entity.dto.Result;
import com.yimei.hs.entity.dto.ying.PageYingHuikuanDTO;
import com.yimei.hs.service.ying.YingHuikuanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api/ying")
@RestController
public class YingHuikuanController {

    private static final Logger logger = LoggerFactory.getLogger(YingHuikuanController.class);

    @Autowired
    private YingHuikuanService yingHuikuanService;

    /**
     * 获取所有huikuan
     *
     * @return
     */
    @GetMapping("/{orderId}/huikuans")
    public ResponseEntity<PageResult<YingHuikuan>> list(PageYingHuikuanDTO pageYingHuikuanDTO) {
        return PageResult.ok(yingHuikuanService.getPage(pageYingHuikuanDTO));
    }

    /**
     * 获取huikuan
     *
     * @param id
     * @return
     */
    @GetMapping("/{orderId}/huikuans/{id}")
    public ResponseEntity<Result<YingHuikuan>> read(
            @PathVariable("orderId") long orderId,
            @PathVariable("id") long id
    ) {
        YingHuikuan huikuan = yingHuikuanService.findOne(id);
        if (huikuan == null) {
            return Result.error(4001, "记录不存在", HttpStatus.NOT_FOUND);
        } else {
            return Result.ok(huikuan);
        }
    }

    /**
     * 创建huikuan
     *
     * @return
     */
    @PostMapping("/{orderId}/huikuans")
    public ResponseEntity<Result<YingHuikuan>> create(@PathVariable("orderId") long orderId) {
        return Result.ok(null);
    }

    /**
     * 更新huikuan
     *
     * @return
     */
    @PutMapping("/{orderId}/huikuans/:id")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("orderId") long orderId,
            @PathVariable("id") long id
    ) {
        return Result.ok(1);
    }
}
