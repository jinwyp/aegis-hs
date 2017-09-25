package com.yimei.hs.ying.controller;

import com.yimei.hs.boot.PageResult;
import com.yimei.hs.ying.dto.PageYingOrderPartyDTO;
import com.yimei.hs.ying.entity.YingOrderParty;
import com.yimei.hs.ying.service.YingPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hary on 2017/9/25.
 */

@RequestMapping("/api/ying")
@RestController
public class YingPartyController {

    @Autowired
    YingPartyService yingPartyService;


    /**
     * 获取分页数据party
     *
     * @return
     */
    @GetMapping("/{orderId}/parties")
    public ResponseEntity<PageResult<YingOrderParty>> list(PageYingOrderPartyDTO pageYingOrderPartyDTO) {
        return PageResult.ok(yingPartyService.getPage(pageYingOrderPartyDTO));
    }

}
