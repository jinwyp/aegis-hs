package com.yimei.hs.user.controller;

import com.yimei.hs.HsApplication;
import com.yimei.hs.user.entity.Team;
import com.yimei.hs.user.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by hary on 2017/9/25.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = HsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TeamControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void list() throws Exception {
    }

    @Test
    public void create() throws Exception {
        Team team = new Team();
        team.setDeptId(2L);
        team.setName("我的team");
        Team rtn = testRestTemplate.postForObject("/api/teams", team, Team.class);
    }

    @Test
    public void read() throws Exception {
    }

    @Test
    public void update() throws Exception {
    }

}