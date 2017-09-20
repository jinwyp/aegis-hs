package com.yimei.hs.controller;

import com.yimei.hs.entity.Team;
import com.yimei.hs.entity.dto.PageDTO;
import com.yimei.hs.entity.dto.ResponseData;
import com.yimei.hs.service.DepartmentService;
import com.yimei.hs.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Created by hary on 2017/9/15.
 */
@Controller
@RequestMapping("/api")
public class TeamController {


    @Autowired
    private TeamService service;
    @Autowired
    private DepartmentService mDepartmentService;


    /**
     * 获取所有team
     * 分页参数
     * page=10
     * pageSize=20
     * <p>
     * 查询条件参数
     * name   :  模糊查询
     *
     * @return
     */
    @GetMapping("/teams")
    public ResponseEntity<PageDTO> list(
            @RequestParam(value = "pageNum", defaultValue = "1", required = false) int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "999", required = false) int pageSize,
            @RequestParam(value = "deptId", required = false) Long deptId
    ) {
        ArrayList<Team> allTeams = service.getTeamPage(pageSize, pageNum, deptId);
        PageDTO page = new PageDTO();
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page.setResponsedata(allTeams);
        return new ResponseEntity<PageDTO>(page, HttpStatus.OK);
    }


    /**
     * 创建team
     *
     * @return
     */
    @PostMapping("/teams")
    public ResponseEntity<Team> create(@RequestBody Team team) {
        ResponseEntity teamResponseEntity;

        if (mDepartmentService.checkDepatIsExit(team.getDeptId())) {
            service.createTeams(team);
            teamResponseEntity = new ResponseEntity<Team>(team, HttpStatus.OK);
        } else {
            teamResponseEntity = new ResponseEntity("添加失败", HttpStatus.OK);
        }
        return teamResponseEntity;
    }

    /**
     * 获取指定id的team
     *
     * @param id
     * @return
     */
    @GetMapping("/teams/{id}")
    public ResponseEntity<Team> read(@PathVariable("id") long id) {
        return new ResponseEntity(service.findTeamByid(id), HttpStatus.OK);
    }

    /**
     * 更新team
     *
     * @return
     */
    @PutMapping("/teams/id")
    public ResponseEntity<ResponseData> update(@RequestParam(value = "id", required = true) long id, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "deptId", required = false) long deptId) {
        ResponseData responseData = new ResponseData();
        if (service.checkTeamExist(id)) {
            Team team = service.findTeamByid(id);
            team.setDeptId(deptId);
            team.setName(name);
            service.updateTeam(team);
            responseData.setRmg("更新成功");
            responseData.setData(team);
        } else {
            responseData.setRmg("数据不存在");
        }
        return new ResponseEntity<ResponseData>(responseData, HttpStatus.OK);
    }


    /**
     * 删除某个Team
     */
    @DeleteMapping("/teams/{id}")
    public String delete(@PathVariable("id") long id) {
        service.deleteTeamById(id);
        return "Team";
    }

}
