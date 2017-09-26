package com.yimei.hs.user.controller;

import com.yimei.hs.HsApplication;
import com.yimei.hs.boot.api.PageResult;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.user.entity.Team;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hary on 2017/9/25.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = HsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional(readOnly = true)
public class TeamControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void list() throws Exception {
        PageResult<Team> page = testRestTemplate.getForObject("/api/teams", PageResult.class);
        System.out.println("get page = " + page.toString());
    }

    @Test
    public void create() throws Exception {
        Team team = new Team();
        team.setDeptId(2L);
        team.setName("我的team");
        Result<Team> rtn = testRestTemplate.postForObject("/api/teams", team, Result.class);
        System.out.println("create return " + rtn.toString());
    }

    @Test
    public void read() throws Exception {
        Result<Team> rtn = testRestTemplate.getForObject("/api/teams/1", Result.class);
        System.out.println("rtn = " + rtn.toString());
    }

    @Test
    public void update() throws Exception {
    }

}