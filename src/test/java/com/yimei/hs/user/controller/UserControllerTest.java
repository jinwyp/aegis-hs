package com.yimei.hs.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.hs.HsApplication;
import com.yimei.hs.boot.PageResult;
import com.yimei.hs.boot.Result;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.CargoType;
import com.yimei.hs.enums.CustomerType;
import com.yimei.hs.enums.SettleMode;
import com.yimei.hs.user.entity.Dept;
import com.yimei.hs.user.entity.Party;
import com.yimei.hs.user.entity.Team;
import com.yimei.hs.user.entity.User;
import com.yimei.hs.util.Digests;
import com.yimei.hs.util.Encodes;
import com.yimei.hs.ying.dto.PageYingOrderDTO;
import com.yimei.hs.ying.entity.YingOrder;
import com.yimei.hs.ying.entity.YingOrderConfig;
import com.yimei.hs.ying.entity.YingOrderParty;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.assertj.core.util.Lists;
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

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @Autowired
    ObjectMapper objectMapper;

    public <T> String printJson(T m) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(m);
    }


//    @PostConstruct
//    void postContruct() {
//        objectMapper.setDefaultPrettyPrinter()
//    }

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
                ParameterizedType pt = ParameterizedTypeImpl.make(Result.class, responseWrapper, null);
                return pt;
            }
        }).getBody();
    }

    public <M, R> Result<R> get(String url, M model, Class<?> classz) {
        return client.exchange(
                url, HttpMethod.GET,
                /*new HttpEntity<M>(model)*/null,
                new ParameterizedTypeReference<Result<R>>() {

                    @Override
                    public Type getType() {
                        Type[] responseWrapper = {classz};
                        ParameterizedType pt = ParameterizedTypeImpl.make(Result.class, responseWrapper, null);
                        return super.getType();
                    }
                }).getBody();
    }

    public <M, R> PageResult<R> getPage(String url, M model, Class<?> classz) {
        return client.exchange(
                url, HttpMethod.GET,
                null, //                new HttpEntity<M>(model),
                new ParameterizedTypeReference<PageResult<R>>() {
                    @Override
                    public Type getType() {
                        Type[] responseWrapper = {classz};
                        ParameterizedType pt = ParameterizedTypeImpl.make(PageResult.class, responseWrapper, null);
                        return super.getType();
                    }
                }).getBody();
    }

    private List<Long> createParties(List<Party> parties) {
        List<Long> rtn = new ArrayList<Long>();
        for (Party p : parties) {

            Result<Party> pt = post("/api/parties", p, Party.class);
            if (pt.getSuccess()) {
                logger.info("创建party成功: {}", pt.getData());
                rtn.add(pt.getData().getId());
            } else {
                logger.error("创建party失败: {}", pt.getError());
                System.exit(-1);
            }
        }
        return rtn;
    }

    @Test
    public void userTest() throws JsonProcessingException {

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
        List<Long> partyIds = createParties(parties);

        // 3. 创建部门
        Dept dept = new Dept();
        dept.setName("周超团队");
        Result<Dept> deptResult = post("/api/departments", dept, Dept.class);
        if (deptResult.getSuccess()) {
            logger.info("创建部门成功: {}", deptResult.getData());
        } else {
            logger.error("创建部门失败: {}", deptResult.getError());
            System.exit(-3);
        }

        // 4. 创建team
        Team team = new Team();
        team.setDeptId(deptResult.getData().getId());
        team.setName("我的team");
        Result<Team> teamResult = post("/api/teams", team, Team.class);
        if (teamResult.getSuccess()) {
            logger.info("创建团队成功: {}", teamResult.getData());
        } else {
            logger.error("创建团队失败: {}", teamResult.getError());
        }

        // 5. 创建用户
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

        // 6. 用户登录
        Result<String> loginResult = post("/api/login", newUser, String.class);
        if (loginResult.getSuccess()) {
            logger.info("登录成功: {}", loginResult.getData());
        } else {
            logger.error("登录失败: {}", loginResult.getError());
        }
        setClientInterceptor(loginResult.getData());

        // 7. 创建应收订单
        YingOrder yingOrder = new YingOrder();
        yingOrder.setTeamId(1L); // 赵善文团队
        yingOrder.setCargoType(CargoType.COAL);
        yingOrder.setUpstreamId(partyIds.get(0));   // 上游
        yingOrder.setDownstreamId(partyIds.get(1));  // 下游
        yingOrder.setMainAccounting(2L);
        yingOrder.setLine("博泰-鲤鱼江");
        yingOrder.setUpstreamSettleMode(SettleMode.ONE_PAPER_SETTLE);
        yingOrder.setDownstreamSettleMode(SettleMode.ONE_PAPER_SETTLE);
        yingOrder.setOrderConfigList(Lists.newArrayList(
                new YingOrderConfig() {{
                    setHsMonth("201706");
                    setContractBaseInterest(new BigDecimal("0.20"));
                    setMaxPrepayRate(new BigDecimal("0.90"));
                    setUnInvoicedRate(new BigDecimal("0.7"));
                    setExpectHKDays(45);
                    setTradeAddPrice(new BigDecimal("0"));
                    setWeightedPrice(new BigDecimal("612"));
                }}
        ));
        yingOrder.setOrderPartyList(Lists.newArrayList(
                new YingOrderParty() {{
                    setCustomerId(17L);
                    setCustType(CustomerType.FUNDER);
                }},
                new YingOrderParty() {{
                    setCustomerId(1L);
                    setCustType(CustomerType.ACCOUNTING_COMPANY);
                }}
        ));

        Result<YingOrder> yingOrderResult = post("/api/yings", yingOrder, YingOrder.class);
        if (yingOrderResult.getSuccess()) {
            logger.info("创建应收订单成功: {}", yingOrderResult.getData());
        } else {
            logger.error("创建应收订单失败: {}", yingOrderResult.getError());
        }

        // 8. 分页查询order
        PageYingOrderDTO pageYingOrderDTO = new PageYingOrderDTO();
        PageResult<YingOrder> yingOrderPageResult = getPage("/api/yings", pageYingOrderDTO, YingOrder.class);
        if (yingOrderPageResult.getSuccess()) {
            logger.info("获取应收订单分页成功 GET /api/yings request: {}\nresponse:\n{}", printJson(pageYingOrderDTO), printJson(yingOrderPageResult.getData()));
        } else {
            logger.error("获取应收订单分页失败: {}", yingOrderPageResult.getError());
            System.exit(-1);
        }

        // 9. 单个查询
        Result<YingOrder> yingOrderResult1 = get("/api/yings/" + yingOrderResult.getData().getId(), 1, YingOrder.class);
        if (yingOrderResult1.getSuccess()) {
            logger.info("获取订单成功/api/yings/{}: {}", yingOrderResult.getData().getId(), printJson(yingOrderResult1.getData()));
        } else {
            logger.error("获取订单失败: {}", yingOrderResult1.getError());
            System.exit(-1);
        }

        // 10. 增加核算月配置
        String url = "/api/ying/" + yingOrderResult1.getData().getId() + "/configs";
        YingOrderConfig config = new YingOrderConfig() {{
            setContractBaseInterest(new BigDecimal("0.20"));
            setMaxPrepayRate(new BigDecimal("0.90"));
            setUnInvoicedRate(new BigDecimal("0.7"));
            setExpectHKDays(45);
            setTradeAddPrice(new BigDecimal("0"));
            setWeightedPrice(new BigDecimal("612"));
        }};
        Result<YingOrderConfig> yingOrderConfigResult = post(url, config, YingOrderConfig.class);
        if (yingOrderConfigResult.getSuccess()) {
            logger.info("添加核算月配置成功 POST {}\nrequest:{}\nresponse:{}", url, printJson(config), printJson(yingOrderConfigResult.getData()));
        } else {
            logger.error("添加核算月配置失败: {}", yingOrderConfigResult.getError());
            System.exit(-2);
        }

    }

}