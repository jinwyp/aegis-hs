package com.yimei.hs.user.controller;

import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.user.dto.PageTeamDTO;
import com.yimei.hs.user.entity.Team;
import com.yimei.hs.user.service.DeptService;
import com.yimei.hs.user.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hary on 2017/9/15.
 */
@Controller
@RequestMapping("/api")
public class TeamController {

    private static final Logger logger = LoggerFactory.getLogger(TeamController.class);

    @Autowired
    private TeamService teamService;

    @Autowired
    private DeptService deptService;


    /**
     * @param pageTeamDTO
     * @return
     */
    @GetMapping("/teams")
    public ResponseEntity<Result<Page<Team>>> list(PageTeamDTO pageTeamDTO) {
        logger.info("get teams: {}", pageTeamDTO);
        return Result.ok(teamService.getPage(pageTeamDTO));
    }


    /**
     * 创建team
     *
     * @return
     */
    @PostMapping("/teams")
    public ResponseEntity<Result<Team>> create(@RequestBody Team team) {
        ResponseEntity teamResponseEntity;

        if (deptService.checkDeptExist(team.getDeptId())) {
            teamService.create(team);
            return Result.ok(team);
        } else {
            return Result.error(5001, "添加失败");
        }
    }

    /**
     * 获取指定id的team
     *
     * @param id
     * @return
     */
    @GetMapping("/teams/{id}")
    public ResponseEntity<Result<Team>> read(@PathVariable("id") long id) {
        Team team = teamService.findOne(id);
        if (team == null) {
            return Result.error(4001, "记录不存在", HttpStatus.NOT_FOUND);
        }
        return Result.ok(team);
    }

    /**
     * 更新team
     *
     * @return
     */
    @PutMapping("/teams/{id}")
    public ResponseEntity<Result<Integer>> update(
            @PathVariable("id") Long id,
            @RequestBody Team team) {
        team.setId(id);
        int status = teamService.update(team);
        if (status == 1) {
            return Result.ok(1);
        } else {
            return Result.error(5003, "更新失败");
        }
    }


    /**
     * 删除某个Team
     */
    @DeleteMapping("/teams/{id}")
    public ResponseEntity<Result<Integer>> delete(@PathVariable("id") long id) {

        logger.debug("teamid -> {}",id);
        int rtn = teamService.delete(id);
        if (rtn != 1) {
            return Result.error(4001, "删除失败", HttpStatus.NOT_FOUND);
        }
        return Result.ok(1);
    }

}
