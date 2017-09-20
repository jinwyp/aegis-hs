package com.yimei.hs.controller;

import com.yimei.hs.entity.Team;
import com.yimei.hs.service.DepartmentService;
import com.yimei.hs.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
     *
     * 查询条件参数
     * name   :  模糊查询
     *
     * @return
     */
    @GetMapping("/teams")
    public ResponseEntity<List<Team>> list(
        @QueryParam("page") int page, 
        @QueryParam("pageSize") int pageSize
    ) {
        ArrayList<Team> allTeams = service.getAllTeams();
        return new ResponseEntity<>(allTeams, HttpStatus.OK);
    }

    /**
     * 获取指定id的team
     *
     * @param id
     * @return
     */
    @GetMapping("/teams/:id")
    public ResponseEntity<Team> read(@RequestParam("id") long id) {
        return new ResponseEntity(service.findTeamByid(id), HttpStatus.OK);
    }

    /**
     * 创建team
     *
     * /api/teams/create?
     *
     * @return
     */
    @PostMapping("/teams")
    public ResponseEntity<Team> create(@RequestBody Team team) {
        int wT=-1;
        if (mDepartmentService.checkDepatIsExit(team.getDeptId())) {
            wT = service.createTeams(team);
        } else {
            wT = 0;
        }
        team.setId(10); 
        return new ResponseEntity(team, HttpStatus.OK);
    }

    /**
     * 更新team
     *
     * @return
     */
    @PutMapping("/teams/:id")
    public String update() {
        return "team";
    }

    @RequestMapping(value = "/team/page", method = RequestMethod.GET)
    public ResponseEntity getTeamPage(@RequestParam("pageSize") int pageSize, @RequestParam("pageNum") int pageNum) {
        ArrayList<Team> allTeams = service.getTeamPage(pageSize, pageNum);
        return new ResponseEntity(allTeams, HttpStatus.OK);
    }

}
