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
     *
     * @return
     */
    @GetMapping("/teams")

    public ResponseEntity<List<Team>> list() {
        ArrayList<Team> allTeams = service.getAllTeams();

        return new ResponseEntity<>(allTeams, HttpStatus.OK);
    }

    /**
     * 获取team
     *
     * @param id
     * @return
     */
    @GetMapping("/team/:id")
    public ResponseEntity read(@RequestParam("id") long id) {

        return new ResponseEntity(service.findTeamByid(id), HttpStatus.OK);
    }

    /**
     * 创建team
     *
     * /api/teams/create?
     *
     * @return
     */
    @PostMapping(value = "/teams/create")
    public ResponseEntity create(@RequestBody Team team) {
        int wT=-1;
        if (mDepartmentService.checkDepatIsExit(team.getDeptId())) {
            wT = service.createTeams(team);
        } else {
            wT = 0;
        }
        return new ResponseEntity(""+wT,HttpStatus.OK);
    }

    /**
     * 更新team
     *
     * @return
     */
    @PutMapping("/team/:id")
    public String update() {
        return "team";
    }

    @RequestMapping(value = "/team/page", method = RequestMethod.GET)
    public ResponseEntity getTeamPage(@RequestParam("pageSize") int pageSize, @RequestParam("pageNum") int pageNum) {
        ArrayList<Team> allTeams = service.getTeamPage(pageSize, pageNum);
        return new ResponseEntity(allTeams, HttpStatus.OK);
    }

}
