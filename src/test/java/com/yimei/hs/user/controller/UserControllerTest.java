package com.yimei.hs.user.controller;

import com.yimei.hs.HsApplication;
import com.yimei.hs.boot.PageResult;
import com.yimei.hs.boot.Result;
import com.yimei.hs.user.entity.Dept;
import com.yimei.hs.user.entity.Team;
import com.yimei.hs.user.entity.User;
import com.yimei.hs.util.Digests;
import com.yimei.hs.util.Encodes;
import com.yimei.hs.ying.entity.YingOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Created by hary on 2017/9/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = HsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional(readOnly = true)
public class UserControllerTest {

    @Autowired
    TestRestTemplate client;

    private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

    private void setClientInterceptor(String bearer) {
        ClientHttpRequestInterceptor interceptor = new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
                request.getHeaders().add("Authorization", bearer);
                return execution.execute(request, body);
            }
        };

        client.getRestTemplate().setInterceptors(Collections.singletonList(interceptor));
    }

    public static final int HASH_INTERATIONS = 1024;
    public static final int SALT_SIZE = 8;

    @Test
    public void initUser() {

        User user = new User();
        user.setDeptId(1L);
        user.setPhone("13022117050");
        user.setPassword("123456");

        byte[] passwordSalt = Digests.generateSalt(SALT_SIZE);
        byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), passwordSalt, HASH_INTERATIONS);

        user.setIsActive(true);
        user.setIsAdmin(false);
        user.setPassword(Encodes.encodeHex(hashPassword));
        user.setPasswordSalt(Encodes.encodeHex(passwordSalt));
        user.setCreateDate(LocalDateTime.now());
        user.setCreateBy("sys");

        logger.info("create user: {}", user);

    }

    public <M, R> Result<R> post(String url, M model, Class<?> classz) {
        return client.exchange(url, HttpMethod.POST, new HttpEntity<M>(model), new ParameterizedTypeReference<Result<R>>() {
            @Override
            public Type getType() {
                Type[] responseWrapper = {classz};
                ParameterizedType  pt = ParameterizedTypeImpl.make(Result.class, responseWrapper, null);
                return pt;
            }
        }).getBody();
    }

    public <M, R> Result<R> get(String url, M model, Class<?> classz) {
        return client.exchange(url, HttpMethod.GET, new HttpEntity<M>(model), new ParameterizedTypeReference<Result<R>>() {

            @Override
            public Type getType() {
                Type[] responseWrapper = {classz};
                ParameterizedType  pt = ParameterizedTypeImpl.make(Result.class, responseWrapper, null);
                return super.getType();
            }
        }).getBody();
    }

    public <M, R> PageResult<R> getPage(String url, M model, Class<?> classz) {
        return client.exchange(url, HttpMethod.GET, new HttpEntity<M>(model), new ParameterizedTypeReference<PageResult<R>>() {
            @Override
            public Type getType() {
                Type[] responseWrapper = {classz};
                ParameterizedType  pt = ParameterizedTypeImpl.make(Result.class, responseWrapper, null);
                return super.getType();
            }
        }).getBody();
    }

    @Test
    public void userTest() {

        // 默认有个admin用户了 13022117050
        // 1. 13022117050用户登录
        User admin = new User();
        admin.setPhone("13022117050");
        admin.setPassword("123456");
        Result<String> result = post("/api/login", admin, String.class);
        if (result.getSuccess()) {
            logger.info("登录成功: {}", result.getData());
        } else {
            logger.error("admin 登录失败");
            System.exit(-1);
        }
        setClientInterceptor(result.getData());

        // 2. 创建部门
        Dept dept = new Dept();
        dept.setName("周超团队");
        Result<Dept> deptResult = post("/api/departments", dept, Dept.class);
        if (deptResult.getSuccess()) {
            logger.info("创建部门成功: {}", deptResult.getData());
        } else {
            logger.error("创建部门失败: {}", deptResult.getError());
            System.exit(-3);
        }

        // 3. 创建team
        Team team = new Team();
        team.setDeptId(deptResult.getData().getId());
        team.setName("我的team");
        Result<Team> teamResult = post("/api/teams", team, Team.class);
        if (teamResult.getSuccess()) {
            logger.info("创建团队成功: {}", teamResult.getData());
        } else {
            logger.error("创建团队失败: {}", teamResult.getError());
        }

        // 4. 创建用户
        User newUser = new User();
        newUser.setPassword("123456");
        newUser.setPhone("13022117051");
        newUser.setDeptId(deptResult.getData().getId());
        Result<User> nuser = post("/api/users", newUser, User.class);
        if (nuser.getSuccess()) {
            logger.info("创建用户成功: {}", nuser.getClass());
        } else {
            logger.error("创建用户失败: {}", nuser.getError());
            System.exit(-2);
        }

        // 5. 用户登录
        Result<String> loginResult = post("/api/login", newUser, String.class);
        if (loginResult.getSuccess()) {
            logger.info("登录成功: {}", loginResult.getData());
        } else {
            logger.error("登录失败: {}", loginResult.getError());
        }
        setClientInterceptor(loginResult.getData());

        // 6. 创建应收订单
        // YingOrder yingOrder = new YingOrder();
        // Result<YingOrder> yingOrderResult = client.postForObject("/api/yings", yingOrder, Result.class);
    }

}