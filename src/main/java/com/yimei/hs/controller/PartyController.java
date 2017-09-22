package com.yimei.hs.controller;


import com.yimei.hs.entity.Party;
import com.yimei.hs.entity.dto.PageResult;
import com.yimei.hs.entity.dto.PagePartyDTO;
import com.yimei.hs.entity.dto.Result;
import com.yimei.hs.service.PartyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/partys")
    public ResponseEntity<PageResult<Party>> list(PagePartyDTO pagePartyDTO) {
        return PageResult.ok(partyService.getPage(pagePartyDTO));
    }

    /**
     * 获取party
     *
     * @param id
     * @return
     */
    @GetMapping("/partys/{id}")
    public ResponseEntity<Result<Party>> read(@PathVariable long id) {

        return Result.ok(partyService.selectPartByid(id));
    }

    /**
     * 创建party
     *
     * @return
     */
    @PostMapping("/partys")
    public ResponseEntity<Result<Party>> create(@RequestBody Party party) {
        logger.warn(party.getName()+"简称"+party.getShortName());
        partyService.create(party);
        return Result.ok(party);
    }

    /**
     * 更新party
     *
     * @return
     */
    @PutMapping("/partys/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("id") long id,
            @RequestParam("shortName") String shotName,
            @RequestParam("name") String name,
            @RequestParam("custType") Integer custType) {

        Party party = new Party();
        party.setPartyType(custType);
        party.setName(name);
        party.setShortName(shotName);
        int status = partyService.updateParty(party);
        if (status == 1) {
            return Result.ok(1);
        } else {
            return Result.error(5003, "更新失败");
        }
    }
}
