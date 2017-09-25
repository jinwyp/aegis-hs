package com.yimei.hs.ying.controller;

import com.yimei.hs.boot.PageResult;
import com.yimei.hs.ying.dto.PageYingOrderConfigDTO;
import com.yimei.hs.ying.dto.PageYingOrderPartyDTO;
import com.yimei.hs.ying.entity.YingOrderConfig;
import com.yimei.hs.ying.entity.YingOrderParty;
import com.yimei.hs.ying.service.YingOrderConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hary on 2017/9/25.
 */
@RestController
@RequestMapping("/api/ying")
public class YingOrderConfigController {

    @Autowired
    YingOrderConfigService yingOrderConfigService;


    /**
     * 获取分页数据party
     *
     * @return
     */
    @GetMapping("/{orderId}/config")
    public ResponseEntity<PageResult<YingOrderConfig>> list(PageYingOrderConfigDTO pageYingOrderConfigDTO) {
        return PageResult.ok(yingOrderConfigService.getPage(pageYingOrderConfigDTO));
    }

}
