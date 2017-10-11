package com.yimei.hs.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.entity.*;
import com.yimei.hs.user.entity.Dept;
import com.yimei.hs.user.entity.Party;
import com.yimei.hs.user.entity.Team;
import com.yimei.hs.user.entity.User;
import com.yimei.hs.util.Digests;
import com.yimei.hs.util.Encodes;
import com.yimei.hs.ying.entity.*;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hary on 2017/9/26.
 */
abstract public class HsTestBase {

    public abstract ObjectMapper getObjectMapper();
    public abstract TestRestTemplate getTestRestTemplate();
    public abstract Logger getLogger();

    public <T> String printJson(T m) throws JsonProcessingException {
        return getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(m);
    }

    public void setClientInterceptor(String bearer) {
        ClientHttpRequestInterceptor interceptor = new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
                request.getHeaders().add("Authorization", bearer);
                return execution.execute(request, body);
            }
        };
        getTestRestTemplate().getRestTemplate().setInterceptors(Collections.singletonList(interceptor));
    }

    protected ParameterizedTypeReference<Result<Boolean>> typeReferenceBoolean  = new ParameterizedTypeReference<Result<Boolean>>() {};
    protected ParameterizedTypeReference<Result<Page<Boolean>>> typeReferenceBooleanPage  = new ParameterizedTypeReference<Result<Page<Boolean>>>() {};

    protected ParameterizedTypeReference<Result<Integer>> typeReferenceInteger  = new ParameterizedTypeReference<Result<Integer>>() {};
    protected ParameterizedTypeReference<Result<Page<Integer>>> typeReferenceIntegerPage  = new ParameterizedTypeReference<Result<Page<Integer>>>() {};

    protected ParameterizedTypeReference<Result<String>> typeReferenceString  = new ParameterizedTypeReference<Result<String>>() {};
    protected ParameterizedTypeReference<Result<Page<String>>> typeReferenceStringPage  = new ParameterizedTypeReference<Result<Page<String>>>() {};

    protected ParameterizedTypeReference<Result<User>> typeReferenceUser  = new ParameterizedTypeReference<Result<User>>() {};
    protected ParameterizedTypeReference<Result<Team>> typeReferenceTeam  = new ParameterizedTypeReference<Result<Team>>() {};
    protected ParameterizedTypeReference<Result<Dept>> typeReferenceDept  = new ParameterizedTypeReference<Result<Dept>>() {};
    protected ParameterizedTypeReference<Result<Party>> typeReferenceParty  = new ParameterizedTypeReference<Result<Party>>() {};

    protected ParameterizedTypeReference<Result<Page<User>>> typeReferenceUserPage  = new ParameterizedTypeReference<Result<Page<User>>>() {};
    protected ParameterizedTypeReference<Result<Page<Team>>> typeReferenceTeamPage  = new ParameterizedTypeReference<Result<Page<Team>>>() {};
    protected ParameterizedTypeReference<Result<Page<Dept>>> typeReferenceDeptPage  = new ParameterizedTypeReference<Result<Page<Dept>>>() {};
    protected ParameterizedTypeReference<Result<Page<Party>>> typeReferencePartyPage  = new ParameterizedTypeReference<Result<Page<Party>>>() {};


    protected ParameterizedTypeReference<Result<List<User>>> typeReferenceUserList  = new ParameterizedTypeReference<Result<List<User>>>() {};

    protected ParameterizedTypeReference<Result<Order>> typeReferenceOrder  = new ParameterizedTypeReference<Result<Order>>() {};
    protected ParameterizedTypeReference<Result<Page<Order>>> typeReferenceOrderPage  = new ParameterizedTypeReference<Result<Page<Order>>>() {};

    protected ParameterizedTypeReference<Result<OrderParty>> typeReferenceOrderParty  = new ParameterizedTypeReference<Result<OrderParty>>() {};
    protected ParameterizedTypeReference<Result<Page<OrderParty>>> typeReferenceOrderPartyPage  = new ParameterizedTypeReference<Result<Page<OrderParty>>>() {};

    protected ParameterizedTypeReference<Result<OrderConfig>> typeReferenceOrderConfig  = new ParameterizedTypeReference<Result<OrderConfig>>() {};
    protected ParameterizedTypeReference<Result<Page<OrderConfig>>> typeReferenceOrderConfigPage  = new ParameterizedTypeReference<Result<Page<OrderConfig>>>() {};

    protected ParameterizedTypeReference<Result<Fee>> typeReferenceFee  = new ParameterizedTypeReference<Result<Fee>>() {};
    protected ParameterizedTypeReference<Result<Page<Fee>>> typeReferenceFeePage  = new ParameterizedTypeReference<Result<Page<Fee>>>() {};


    protected ParameterizedTypeReference<Result<SettleTraffic>> typeReferenceSettleTraffic  = new ParameterizedTypeReference<Result<SettleTraffic>>() {};
    protected ParameterizedTypeReference<Result<Page<SettleTraffic>>> typeReferenceSettleTrafficPage  = new ParameterizedTypeReference<Result<Page<SettleTraffic>>>() {};





    public void createUser(String phone, String password) {

        final int HASH_INTERATIONS = 1024;
        final int SALT_SIZE = 8;

        User user = new User();
        user.setDeptId(1L);
        user.setPhone(phone);
        user.setPassword(password);

        byte[] passwordSalt = Digests.generateSalt(SALT_SIZE);
        byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), passwordSalt, HASH_INTERATIONS);

        user.setIsActive(true);
        user.setIsAdmin(false);
        user.setPassword(Encodes.encodeHex(hashPassword));
        user.setPasswordSalt(Encodes.encodeHex(passwordSalt));
        user.setCreateDate(LocalDateTime.now());
        user.setCreateBy("sys");

        // System.out.println("create user: " + user);
    }


    protected List<Long> createParties(List<Party> parties) throws JsonProcessingException {
        List<Long> rtn = new ArrayList<Long>();
        for (Party p : parties) {

            Result<Party> pt = getTestRestTemplate().exchange("/api/parties", HttpMethod.POST, new HttpEntity<Party>(p), typeReferenceParty).getBody();
            if (pt.getSuccess()) {
                getLogger().info("创建party成功\nPOST {}\nrequest = {}\nresponse = {} ", "/api/parties", printJson(p), printJson(pt.getData()));
                rtn.add(pt.getData().getId());
            } else {
                getLogger().info("创建party失败: " + pt.getError());
                System.exit(-1);
            }
        }
        return rtn;
    }

    protected List<Long> partyIds = null;
    protected Result<User> userResult = null;

    protected void defaultUser() throws JsonProcessingException {

        TestRestTemplate client = getTestRestTemplate();
        Logger logger = getLogger();

        // 默认有个admin用户了 13022117050
        // 1. 13022117050用户登录
        User admin = new User();
        admin.setPhone("13022117050");
        admin.setPassword("123456");

        Result<String> result = client.exchange("/api/login", HttpMethod.POST, new HttpEntity<User>(admin), typeReferenceString).getBody();

        if (result.getSuccess()) {
            logger.info("登录成功\nPOST {}\nrequest = {}\nresponse = {}", "/api/login", printJson(admin), printJson(result.getData()));
        } else {
            logger.error("admin 登录失败");
            System.exit(-1);
        }
        setClientInterceptor(result.getData());

        // 2. 创建hs_party
        List<Party> parties = Lists.newArrayList(
                new Party() {{
                    setName("郴州博太超细石墨股份有限公司");
                    setShortName("博泰");
                    setPartyType(3);
                }},
                new Party() {{
                    setName("湖南华润电力鲤鱼江有限公司");
                    setShortName("鲤鱼江");
                    setPartyType(3);
                }}
        );
        partyIds = createParties(parties);

        // 3. 创建部门
        Dept dept = new Dept();
        dept.setName("周超团队");
        Result<Dept> deptResult = client.exchange("/api/departments", HttpMethod.POST, new HttpEntity<Dept>(dept), typeReferenceDept).getBody();
        if (deptResult.getSuccess()) {
            logger.info("创建部门成功\nPOST {}\nrequest = {}\nresponse = {}", "/api/departments", printJson(dept), printJson(deptResult.getData()));
        } else {
            logger.error("创建部门失败: {}", deptResult.getError());
            System.exit(-3);
        }

        // 4. 创建team
        Team team = new Team();
        team.setDeptId(deptResult.getData().getId());
        team.setName("我的team");
        Result<Team> teamResult = client.exchange("/api/teams", HttpMethod.POST, new HttpEntity<Team>(team), typeReferenceTeam).getBody();
        if (teamResult.getSuccess()) {
            logger.info("创建团队成功\nPOST {}\nrequest = {}\nresponse = {}", "/api/teams", printJson(team), printJson(teamResult.getData()));
        } else {
            logger.error("创建团队失败: {}", teamResult.getError());
        }

        // 5. 创建用户
        User newUser = new User();
        newUser.setPassword("123456");
        newUser.setPhone("13022117051");
        newUser.setDeptId(deptResult.getData().getId());
        userResult = client.exchange("/api/users", HttpMethod.POST, new HttpEntity<User>(newUser), typeReferenceUser).getBody();
        if (userResult.getSuccess()) {
            logger.info("创建用户成功\nPOST {}\nrequest = {}\nresponse = {}", "/api/users", printJson(newUser), printJson(userResult.getData()));
        } else {
            logger.error("创建用户失败: {}", userResult.getError());
            System.exit(-2);
        }

        // 5.1 更新用户
        User updateUser = new User();
        updateUser.setId(userResult.getData().getId());
        updateUser.setIsActive(true);
        updateUser.setDeptId(userResult.getData().getDeptId());
        String updateUserURL = "/api/users/" + userResult.getData().getId();
        Result<Integer> updateUserRtn = client.exchange(updateUserURL, HttpMethod.PUT, new HttpEntity<User>(updateUser), typeReferenceInteger).getBody();
        if (updateUserRtn.getSuccess()) {
            logger.info("更新用户成功\nPUT {}\nrequest = {}\nresponse = {}", updateUserURL, printJson(updateUser), printJson(updateUserRtn.getData()));
        } else {
            logger.error("更新用户失败: {}", updateUserRtn.getError());
            System.exit(-2);
        }

        // 6. 用户登录
        Result<String> loginResult = client.exchange("/api/login", HttpMethod.POST, new HttpEntity<User>(newUser), typeReferenceString).getBody();
        if (loginResult.getSuccess()) {
            logger.info("登录成功\nPOST {}\nrequest = {}\nresponse = {}", "/api/login", printJson(newUser), printJson(loginResult.getData()));
        } else {
            logger.error("登录失败: {}", loginResult.getError());
        }
        setClientInterceptor(loginResult.getData());

        // 6.1 更改密码
        User changeUser = new User();
        changeUser.setOldPassword("123456");
        changeUser.setPassword("1234567");
        Result<Integer> changeResult = client.exchange("/api/change_password", HttpMethod.PUT, new HttpEntity<User>(changeUser), typeReferenceInteger).getBody();
        if (changeResult.getSuccess()) {
            logger.info("修改密码成功\nPUT {}\nrequest = {}\nresponse = {}", "/api/change_password", printJson(changeUser), changeResult.getData());
        } else {
            logger.info("修改密码失败: {}", changeResult.getError());
        }

        // 6.2 再登录一次, 用新密码登录
        newUser.setPassword("1234567");
        loginResult = client.exchange("/api/login", HttpMethod.POST, new HttpEntity<User>(newUser), typeReferenceString).getBody();
        if (loginResult.getSuccess()) {
            logger.info("登录成功\nPOST {}\nrequest = {}\nresponse = {}", "/api/login", printJson(newUser), printJson(loginResult.getData()));
        } else {
            logger.error("登录失败: {}", loginResult.getError());
        }
        setClientInterceptor(loginResult.getData());

        // 6.3 登出
        Result<Integer> logoutResult = client.exchange("/api/logout", HttpMethod.GET, HttpEntity.EMPTY, typeReferenceInteger).getBody();
        if (logoutResult.getSuccess()) {
            logger.info("登出成功\nGET {}\nrequest = {}\nresponse = {}", "/api/login", "", logoutResult.getData());
        } else {
            logger.error("登出失败: {}", logoutResult.getError());
        }

        // 6.4 再次重新登录
        newUser.setPassword("1234567");
        loginResult = client.exchange("/api/login", HttpMethod.POST, new HttpEntity<User>(newUser), typeReferenceString).getBody();
        if (loginResult.getSuccess()) {
            logger.info("登录成功\nPOST {}\nrequest = {}\nresponse = {}", "/api/login", printJson(newUser), printJson(loginResult.getData()));
        } else {
            logger.error("登录失败: {}", loginResult.getError());
        }
        setClientInterceptor(loginResult.getData());

    }


}
