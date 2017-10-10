package com.yimei.hs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.enums.CargoType;
import com.yimei.hs.enums.CustomerType;
import com.yimei.hs.enums.SettleMode;
import com.yimei.hs.same.dto.PageOrderDTO;
import com.yimei.hs.same.entity.Order;
import com.yimei.hs.same.entity.OrderConfig;
import com.yimei.hs.same.entity.OrderParty;
import com.yimei.hs.test.HsTestBase;
import com.yimei.hs.user.entity.Dept;
import com.yimei.hs.user.entity.Party;
import com.yimei.hs.user.entity.Team;
import com.yimei.hs.user.entity.User;
import com.yimei.hs.util.WebUtils;
import org.assertj.core.util.Lists;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by hary on 2017/9/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = HsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Ignore
public class YingIntegrationA extends HsTestBase {

    public static final Logger logger = LoggerFactory.getLogger(YingIntegrationA.class);

    @Override
    public Logger getLogger() {
        return logger;
    }

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

    List<Long> partyIds = null;
    Result<User> userResult = null;
    Result<Order> yingOrderResult = null;
    Result<OrderConfig> yingOrderConfigResult = null;

    protected void user() throws JsonProcessingException {
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

    @Test
    public void yingIntegration() throws JsonProcessingException {
        System.out.println("开始应收集成测试");
        user();
        order();
    }

    public void order() throws JsonProcessingException {
        // 7. 创建应收订单
        Order yingOrder = new Order();
        yingOrder.setBusinessType(BusinessType.ying);
        yingOrder.setTeamId(1L); // 赵善文团队
        yingOrder.setDeptId(3L);
        yingOrder.setCargoType(CargoType.COAL);
        yingOrder.setUpstreamId(partyIds.get(0));   // 上游
        yingOrder.setDownstreamId(partyIds.get(1));  // 下游
        yingOrder.setMainAccounting(1L);
        yingOrder.setLine("郴州博太-晋和-华润鲤鱼江");
        yingOrder.setUpstreamSettleMode(SettleMode.ONE_PAPER_SETTLE);
        yingOrder.setDownstreamSettleMode(SettleMode.ONE_PAPER_SETTLE);
        yingOrder.setOrderConfigList(Lists.newArrayList(
                new OrderConfig() {{
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
                new OrderParty() {{
                    setCustomerId(18L);
                    setCustType(CustomerType.FUNDER);
                }},
                new OrderParty() {{
                    setCustomerId(17L);
                    setCustType(CustomerType.FUNDER);
                }},
                new OrderParty() {{
                    setCustomerId(1L);
                    setCustType(CustomerType.ACCOUNTING_COMPANY);
                }}
        ));
        yingOrderResult = client.exchange("/api/business/yings", HttpMethod.POST, new HttpEntity<Order>(yingOrder), typeReferenceOrder).getBody();
        if (yingOrderResult.getSuccess()) {
            logger.info("创建应收订单成功\nPOST {}\nrequest = {}\nresponse = {}", "/api/yings", printJson(yingOrder), printJson(yingOrderResult.getData()));
        } else {
            logger.error("创建应收订单失败: {}", yingOrderResult.getError());
        }

        // 8. 分页查询order

        Map<String, Object> variables = WebUtils.getUrlVariables(PageOrderDTO.class);
        variables.put("ownerId", userResult.getData().getId());
        variables.put("pageSize", 5);
        variables.put("pageNo", 1);
        String orderPageUrl = "/api/business/yings?" + WebUtils.getUrlTemplate(PageOrderDTO.class);
        // logger.info("page order variables = {}, url = {}", variables, orderPageUrl);
        Result<Page<Order>> yingOrderPageResult = client.exchange(orderPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceOrderPage, variables).getBody();
        if (yingOrderPageResult.getSuccess()) {
            logger.info("获取应收订单分页成功\nGET {}\nrequest = {}\nresponse:\n{}", orderPageUrl, printJson(variables), printJson(yingOrderPageResult.getData()));
        } else {
            logger.error("获取应收订单分页失败: {}", yingOrderPageResult.getError());
            System.exit(-1);
        }

        // 9. 单个订单查询
        String kurl = "/api/business/yings/" + yingOrderResult.getData().getId();
        Result<Order> yingOrderResult1 = client.exchange(kurl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceOrder).getBody();
        if (yingOrderResult1.getSuccess()) {
            logger.info("查询订单成功\nGET {}\nrequest = {}\nresponse = {}", kurl, "", printJson(yingOrderResult1.getData()));
        } else {
            logger.error("查询订单失败\nGET {}\nrequest = {}\nerror = {}", kurl, "", yingOrderResult1.getError());
            System.exit(-1);
        }


    }
}
