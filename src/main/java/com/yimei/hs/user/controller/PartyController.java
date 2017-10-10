package com.yimei.hs.user.controller;


import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.user.dto.PagePartyDTO;
import com.yimei.hs.user.entity.Party;
import com.yimei.hs.user.service.PartyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@RestController
@RequestMapping("/api")
public class PartyController {
    private static final Logger logger = LoggerFactory.getLogger(PartyController.class);
    @Autowired
    PartyService partyService;

    /**
     * 获取所有party
     *
     * @return
     */
    @GetMapping("/parties")
    public ResponseEntity<Result<Page<Party>>> list(PagePartyDTO pagePartyDTO) {
        return Result.ok(partyService.getPage(pagePartyDTO));
    }

    /**
     * 获取party
     *
     * @param id
     * @return
     */
    @GetMapping("/parties/{id}")
    public ResponseEntity<Result<Party>> read(@PathVariable long id) {
        Party party = partyService.findOne(id);
        if (party == null) {
            return Result.error(4001, "记录不存在");
        } else {
            return Result.ok(party);
        }
    }

    /**
     * 创建party
     *
     * @return
     */
    @PostMapping("/parties")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<Party>> create(@RequestBody Party party) {
        int rtn = partyService.create(party);
        if (rtn != 1) {
            logger.error("创建party失败: {}", party);
            return Result.error(4001, "创建参与方失败");
        }
        return Result.ok(party);
    }

    /**
     * 更新party
     *
     * @return
     */
    @PutMapping("/parties/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<Integer>> update(
            @PathVariable(value = "id") Long id,
            @RequestBody Party party
    ) {
        party.setId(id);
        int status = partyService.update(party);
        if (status == 1) {
            return Result.ok(1);
        } else {
            return Result.error(5003, "更新失败");
        }
    }
}
