package com.yimei.hs.controller.ying;

import com.yimei.hs.boot.annotation.CurrentUser;
import com.yimei.hs.entity.User;
import com.yimei.hs.entity.YingOrder;
import com.yimei.hs.entity.dto.PageResult;
import com.yimei.hs.entity.dto.Result;
import com.yimei.hs.entity.dto.ying.PageYingOrderDTO;
import com.yimei.hs.enums.OrderStatus;
import com.yimei.hs.service.ying.YingOrderService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RequestMapping("/api/yings")
@RestController
public class YingOrderController {

    private static final Logger logger = LoggerFactory.getLogger(YingOrderController.class);

    @Autowired
    private YingOrderService yingOrderService;

    /**
     * 获取所有order
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<PageResult<YingOrder>> list(PageYingOrderDTO pageYingOrderDTO) {
        return PageResult.ok(yingOrderService.getPage(pageYingOrderDTO));
    }

    /**
     * 获取order
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Result<YingOrder>> read( @PathVariable("id") long id ) {
        return Result.ok(yingOrderService.findOne(id));
    }

    /**
     * 创建order
     *
     * @return
     */
    @PostMapping
    @Transactional(readOnly = false)
    public ResponseEntity<Result<YingOrder>> create(@CurrentUser User user, @RequestBody YingOrder order) {
        order.setCreatorId(user.getId());
        order.setOwnerId(user.getId());
        order.setStatus(OrderStatus.UNCOMPLETED);
        logger.info("my order = {}", order);
        yingOrderService.create(order);
        return Result.ok(order);
    }


    /**
     * 更新order
     *
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("id") long id
    ) {
        //
        return Result.ok(1);
    }
}
