package com.yimei.hs.controller;

import com.yimei.hs.entity.Team;
import com.yimei.hs.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
     * @return
     */
    @GetMapping("/teams")
    public ResponseEntity list() {
        ArrayList<Team> allTeams = service.getAllTeams();

        return  new ResponseEntity(allTeams, HttpStatus.OK);
    }

    /**
     * 获取team
     * @param id
     * @return
     */
    @GetMapping("/team/:id")
    public String read(long id) {
        return "team";
    }

    /**
     * 创建team
     * @return
     */
    @PostMapping("/teams")
    public String create() {
        return "team";
    }

    /**
     *  更新team
     * @return
     */
    @PutMapping("/team/:id")
    public String update() {
        return "team";
    }

}
