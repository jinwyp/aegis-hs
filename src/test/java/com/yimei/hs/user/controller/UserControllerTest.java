package com.yimei.hs.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.hs.HsApplication;
import com.yimei.hs.boot.api.PageResult;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.enums.*;
import com.yimei.hs.test.YingTestBase;
import com.yimei.hs.user.entity.Dept;
import com.yimei.hs.user.entity.Party;
import com.yimei.hs.user.entity.Team;
import com.yimei.hs.user.entity.User;
import com.yimei.hs.ying.dto.PageYingOrderConfigDTO;
import com.yimei.hs.ying.entity.YingFayun;
import com.yimei.hs.ying.entity.YingOrder;
import com.yimei.hs.ying.entity.YingOrderConfig;
import com.yimei.hs.ying.entity.YingOrderParty;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hary on 2017/9/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = HsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional(readOnly = true)
public class UserControllerTest extends YingTestBase {

    @Autowired
    TestRestTemplate client;

    @Autowired
    ObjectMapper objectMapper;


    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @Override
    public TestRestTemplate getTestRestTemplate() {
        return client;
    }


    private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);


    @Test
    public void initUser() {
        createUser("13022117050", "123456");
    }


    @Test
    public void userTest() throws JsonProcessingException {

        // 默认有个admin用户了 13022117050
        // 1. 13022117050用户登录
        User admin = new User();
        admin.setPhone("13022117050");
        admin.setPassword("123456");

        // Result<String> result = post("/api/login", admin, String.class);
        Result<String> result = client.exchange("/api/login", HttpMethod.POST, new HttpEntity<User>(admin), typeReferenceString).getBody();

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
        // Result<Dept> deptResult = post("/api/departments", dept, Dept.class);
        Result<Dept> deptResult = client.exchange("/api/departments", HttpMethod.POST, new HttpEntity<Dept>(dept), typeReferenceDept).getBody();
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
        // Result<Team> teamResult = post("/api/teams", team, Team.class);
        Result<Team> teamResult = client.exchange("/api/teams", HttpMethod.POST, new HttpEntity<Team>(team), typeReferenceTeam).getBody();
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
        // Result<User> nuser = post("/api/users", newUser, User.class);
        Result<User> nuser = client.exchange("/api/users", HttpMethod.POST, new HttpEntity<User>(newUser), typeReferenceUser).getBody();
        if (nuser.getSuccess()) {
            logger.info("创建用户成功: {}", nuser.getClass());
        } else {
            logger.error("创建用户失败: {}", nuser.getError());
            System.exit(-2);
        }

        // 6. 用户登录
        // Result<String> loginResult = post("/api/login", newUser, String.class);
        Result<String> loginResult = client.exchange("/api/login", HttpMethod.POST, new HttpEntity<User>(newUser), typeReferenceString).getBody();
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

        // Result<YingOrder> yingOrderResult = post("/api/yings", yingOrder, YingOrder.class);
        Result<YingOrder> yingOrderResult = client.exchange("/api/yings", HttpMethod.POST, new HttpEntity<YingOrder>(yingOrder), typeReferenceOrder).getBody();
        if (yingOrderResult.getSuccess()) {
            logger.info("创建应收订单成功: {}", yingOrderResult.getData());
        } else {
            logger.error("创建应收订单失败: {}", yingOrderResult.getError());
        }

        // 8. 分页查询order
        Map<String, Object> variables = new HashMap<>();
        variables.put("ownerId", nuser.getData().getId());
        PageResult<YingOrder> yingOrderPageResult = client.exchange("/api/yings", HttpMethod.GET, HttpEntity.EMPTY, typeReferenceOrderPage, variables).getBody();
        if (yingOrderPageResult.getSuccess()) {
            logger.info("获取应收订单分页成功 GET /api/yings request: {}\nresponse:\n{}", variables, printJson(yingOrderPageResult.getData()));
        } else {
            logger.error("获取应收订单分页失败: {}", yingOrderPageResult.getError());
            System.exit(-1);
        }

        // 9. 单个订单查询
        ParameterizedTypeReference<Result<YingOrder>> tf = new ParameterizedTypeReference<Result<YingOrder>>() {
        };
        String kurl = "/api/yings/" + yingOrderResult.getData().getId();
        Result<YingOrder> yingOrderResult1 = client.exchange(kurl, HttpMethod.GET, HttpEntity.EMPTY, tf).getBody();

        // 10. 增加核算月配置
        String url = "/api/ying/" + yingOrderResult1.getData().getId() + "/configs";
        YingOrderConfig config = new YingOrderConfig() {{
            setHsMonth("201712");
            setContractBaseInterest(new BigDecimal("0.20"));
            setMaxPrepayRate(new BigDecimal("0.90"));
            setUnInvoicedRate(new BigDecimal("0.7"));
            setExpectHKDays(45);
            setTradeAddPrice(new BigDecimal("0"));
            setWeightedPrice(new BigDecimal("612"));
        }};
        Result<YingOrderConfig> yingOrderConfigResult = client.exchange(url, HttpMethod.POST, new HttpEntity<YingOrderConfig>(config), typeReferenceOrderConfig).getBody();
        if (yingOrderConfigResult.getSuccess()) {
            logger.info("添加核算月配置成功 POST {}\nrequest:{}\nresponse:{}", url, printJson(config), printJson(yingOrderConfigResult.getData()));
        } else {
            logger.error("添加核算月配置失败: {}", yingOrderConfigResult.getError());
            System.exit(-2);
        }

        // 11. 修改核算月配置

        YingOrderConfig yingOrderConfig = new YingOrderConfig() {{
            setOrderId(yingOrderConfigResult.getData().getOrderId());
            setId(yingOrderConfigResult.getData().getId());
            setHsMonth("201711");
            setContractBaseInterest(new BigDecimal("0.20"));
            setMaxPrepayRate(new BigDecimal("0.90"));
            setUnInvoicedRate(new BigDecimal("0.7"));
            setExpectHKDays(45);
            setTradeAddPrice(new BigDecimal("0"));
            setWeightedPrice(new BigDecimal("700"));
        }};


        String yingOrderCongfigUpdateUrl = "/api/ying/" + yingOrderConfigResult.getData().getOrderId() + "/configs/" + yingOrderConfigResult.getData().getId();
        logger.info("ymurl===>" + yingOrderCongfigUpdateUrl);
        Result<Integer> yingOrderCOnfigUpdateResult = client.exchange(yingOrderCongfigUpdateUrl, HttpMethod.PUT, new HttpEntity<YingOrderConfig>(yingOrderConfig), typeReferenceInteger).getBody();

        if (yingOrderCOnfigUpdateResult.getSuccess()) {
            logger.info("更新核算月配置成功 POST {}\nrequest:{}\nresponse:{}", yingOrderCongfigUpdateUrl, printJson(yingOrderCOnfigUpdateResult), printJson(yingOrderCOnfigUpdateResult.getData()));
        } else {
            logger.error("更新核算月配置失败: {}", yingOrderCOnfigUpdateResult.getError());
            System.exit(-2);
        }

        // 12. 核算月查询 by orderId
//        Map<String, Object> variablesHs = new HashMap<>();
//        variables.put("orderId", yingOrderConfigResult.getData().getOrderId());
////        PageYingOrderConfigDTO configDTO = new PageYingOrderConfigDTO();
////        configDTO.setOrderId(yingOrderConfigResult.getData().getOrderId());
//        PageResult<YingOrderConfig> yingOrderConfigPageResult= client.exchange("/api/ying/"+yingOrderConfigResult.getData().getOrderId()+"/congfigs", HttpMethod.GET, HttpEntity.EMPTY, typeReferenceOrderConfigPage, variables).getBody();
//        if (yingOrderConfigPageResult.getSuccess()) {
//            logger.info("获取核算月配置分页成功 GET /api/yings request: {}\nresponse:\n{}", variablesHs, printJson(yingOrderConfigPageResult.getData()));
//        } else {
//            logger.error("获取核算月配置分页失败: {}", yingOrderConfigPageResult.getError());
//            System.exit(-1);
//        }


        // 13. 创建发运

        YingFayun fayun = new YingFayun() {{
            setOrderId(yingOrderResult.getData().getId());
            setHsId(yingOrderResult.getData().getOrderConfigList().get(0).getId());
            setArriveStatus(CargoArriveStatus.ARRIVE);
            setFyDate(LocalDateTime.now());
            setFyAmount(new BigDecimal("1510.60"));
            setUpstreamTrafficMode(TrafficMode.RAIL);
            setUpstreamJHH("1000001");
            setDownstreamTrafficMode(TrafficMode.MOTOR);
            setDownstreamCars(166);

        }};

        String urlFaYun = "/api/ying/" + yingOrderResult.getData().getId() + "/fayuns";
        Result<YingFayun> yingFayunResult = client.exchange(urlFaYun, HttpMethod.POST, new HttpEntity<YingFayun>(fayun), typeReferenceFayun).getBody();
        if (yingFayunResult.getSuccess()) {
            logger.info("创建发运成功: {}", yingFayunResult.getData());
        } else {
            logger.error("创建发运失败: {}", yingFayunResult.getError());
        }


        // 14. 发运分页查询,  orderId


        // 15. 发运单记录查询

        // 16. 费用创建

        // 17. 费用分页

        // 18. 分页单记录查询

        // 19. 费用更新

        // 20-1. 运输方结算 - 创建

        // 20-2. 运输方结算 - 分页查询

        // 20-3. 运输方结算 - 单记录查询

        // 20-4. 运输方结算 - 更新

        // 上游结算 - 创建

        // 上游结算 - 分页

        // 上游结算 - 单记录

        // 上游结算 - 更新

        // 付款 - 创建
        // 付款 - 分页
        // 付款 - 单记录

        // 发票 - 创建
        // 发票 - 分页
        // 发票 - 单记录
        // 发票 - 更新

        // 发票明细 - 分页
        // 发票明细 - 更新

        // 下游结算 - 创建
        // 下游结算 - 分页
        // 下游结算 - 查询
        // 下游结算 - 更新  todo

        // 回款 - 创建
        // 回款 - 分页
        // 回款 - 查询

        // 还款 - 创建
        // 还款 - 分页
        // 还款 - 查询
    }

}