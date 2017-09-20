package com.yimei.hs.controller;

import com.yimei.hs.entity.Team;
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
    TeamService service;

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
        ArrayList<Team> allTeams = service.getAllTeams(page, pageSize);
        return new ResponseEntity<>(allTeams, HttpStatus.OK);
    }

    /**
     * 获取指定id的team
     *
     * @param id
     * @return
     */
    @GetMapping("/teams/:id")
    public String read(long id) {
        return "team";
    }

    /**
     * 创建team
     *
     * @return
     */
    @PostMapping("/teams")
    public String create(@RequestBody Team team) {
        return "team";
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

    @RequestMapping( value = "/team/page",method = RequestMethod.GET)
    public ResponseEntity getTeamPage(@RequestParam("pageSize") int pageSize, @RequestParam("pageNum") int pageNum) {
        ArrayList<Team> allTeams = service.getTeamPage(pageSize, pageNum);
        return new ResponseEntity(allTeams,HttpStatus.OK);
    }

}
