package com.yimei.hs.controller;

import com.yimei.hs.entity.Team;
import com.yimei.hs.entity.dto.PageResult;
import com.yimei.hs.entity.dto.Result;
import com.yimei.hs.entity.dto.PageTeamDTO;
import com.yimei.hs.service.DepartmentService;
import com.yimei.hs.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Autowired
    private TeamService teamService;
    @Autowired
    private DepartmentService departmentService;


    @GetMapping("/teams")
    public ResponseEntity<PageResult<Team>> list(PageTeamDTO pageTeamDTO
    ) {

        return PageResult.ok(teamService.getTeamPage(pageTeamDTO));
    }


    /**
     * 创建team
     *
     * @return
     */
    @PostMapping("/teams")
    public ResponseEntity<Result<Team>> create(@RequestBody Team team) {
        ResponseEntity teamResponseEntity;

        if (departmentService.checkDeptExist(team.getDeptId())) {
            teamService.createTeams(team);
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
        return Result.ok(teamService.findTeamByid(id));
    }

    /**
     * 更新team
     *
     * @return
     */
    @PutMapping("/teams/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<Result<Integer>> update(@PathVariable(value = "id") long id, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "deptId", required = false) long deptId) {

        Team team = new Team();
        team.setDeptId(deptId);
        team.setName(name);

        int status = teamService.updateTeam(team);
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
    public String delete(@PathVariable("id") long id) {
        teamService.deleteTeamById(id);
        return "Team";
    }

}
