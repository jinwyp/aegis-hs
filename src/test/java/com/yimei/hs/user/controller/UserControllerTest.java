package com.yimei.hs.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.regexp.internal.RE;
import com.yimei.hs.HsApplication;
import com.yimei.hs.boot.api.PageResult;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.enums.*;
import com.yimei.hs.test.YingTestBase;
import com.yimei.hs.user.entity.Dept;
import com.yimei.hs.user.entity.Party;
import com.yimei.hs.user.entity.Team;
import com.yimei.hs.user.entity.User;
import com.yimei.hs.util.WebUtils;
import com.yimei.hs.ying.dto.*;
import com.yimei.hs.ying.entity.*;
import org.apache.tomcat.jni.Local;
import org.assertj.core.util.Lists;
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

import java.beans.IntrospectionException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hary on 2017/9/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = HsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @Transactional(readOnly = true)
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


    @Override
    public Logger getLogger() {
        return logger;
    }

    private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

    List<Long> partyIds = null;
    Result<User> userResult = null;
    Result<YingOrder> yingOrderResult = null;
    Result<YingOrderConfig> yingOrderConfigResult = null;

    @Test
    public void initUser() {
        createUser("13022117050", "123456");
    }


    @Test
    public void adminTest() throws JsonProcessingException {
        // user();
        // PageResult<User> pageUserResult = client.exchange("/api/users", HttpMethod.GET, HttpEntity.EMPTY, typeReferenceUserPage).getBody();
        // logger.info("page user = {}", pageUserResult);
    }

    @Test
    public void userTest() throws JsonProcessingException {

        user();
//        order();
//        config();
//        fayun();
//        fukuan();
//        huikuan();
//        huankuan();
//        upstream();
//        downstream();
//        traffic();
//        fee();
//        invoice();


    }

    private void user() throws JsonProcessingException {
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


//    private void deleteUserAbout()throws JsonProcessingException{
//
//        Result<String> result = client.exchange("/api/login", HttpMethod.POST, new HttpEntity<User>(admin), typeReferenceString).getBody();
//    }

    private void order() throws JsonProcessingException {

        // 7. 创建应收订单
        YingOrder yingOrder = new YingOrder();
        yingOrder.setTeamId(1L); // 赵善文团队
        yingOrder.setCargoType(CargoType.COAL);
        yingOrder.setUpstreamId(partyIds.get(0));   // 上游
        yingOrder.setDownstreamId(partyIds.get(1));  // 下游
        yingOrder.setMainAccounting(1L);
        yingOrder.setLine("郴州博太-晋和-华润鲤鱼江");
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
                    setCustomerId(18L);
                    setCustType(CustomerType.FUNDER);
                }},
                new YingOrderParty() {{
                    setCustomerId(17L);
                    setCustType(CustomerType.FUNDER);
                }},
                new YingOrderParty() {{
                    setCustomerId(1L);
                    setCustType(CustomerType.ACCOUNTING_COMPANY);
                }}
        ));
        yingOrderResult = client.exchange("/api/yings", HttpMethod.POST, new HttpEntity<YingOrder>(yingOrder), typeReferenceOrder).getBody();
        if (yingOrderResult.getSuccess()) {
            logger.info("创建应收订单成功\nPOST {}\nrequest = {}\nresponse = {}", "/api/yings", printJson(yingOrder), printJson(yingOrderResult.getData()));
        } else {
            logger.error("创建应收订单失败: {}", yingOrderResult.getError());
        }

        // 8. 分页查询order

        Map<String, Object> variables = WebUtils.getUrlVariables(PageYingOrderDTO.class);
        variables.put("ownerId", userResult.getData().getId());
        variables.put("pageSize", 5);
        variables.put("pageNo", 1);
        String orderPageUrl = "/api/yings?" + WebUtils.getUrlTemplate(PageYingOrderDTO.class);
        // logger.info("page order variables = {}, url = {}", variables, orderPageUrl);
        PageResult<YingOrder> yingOrderPageResult = client.exchange(orderPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceOrderPage, variables).getBody();
        if (yingOrderPageResult.getSuccess()) {
            logger.info("获取应收订单分页成功\nGET {}\nrequest = {}\nresponse:\n{}", orderPageUrl, printJson(variables), printJson(yingOrderPageResult.getData()));
        } else {
            logger.error("获取应收订单分页失败: {}", yingOrderPageResult.getError());
            System.exit(-1);
        }

        // 9. 单个订单查询
        String kurl = "/api/yings/" + yingOrderResult.getData().getId();
        Result<YingOrder> yingOrderResult1 = client.exchange(kurl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceOrder).getBody();
        if (yingOrderResult1.getSuccess()) {
            logger.info("查询订单成功\nGET {}\nrequest = {}\nresponse = {}", kurl, "", printJson(yingOrderResult1.getData()));
        } else {
            logger.error("查询订单失败\nGET {}\nrequest = {}\nerror = {}", kurl, "", yingOrderResult1.getError());
            System.exit(-1);
        }


    }

    private void config() throws JsonProcessingException {

        // 1. 增加核算月配置
        String configCreateUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/units";

        YingOrderConfig config = new YingOrderConfig() {{
            setHsMonth("201712");
            setContractBaseInterest(new BigDecimal("0.20"));
            setMaxPrepayRate(new BigDecimal("0.90"));
            setUnInvoicedRate(new BigDecimal("0.7"));
            setExpectHKDays(45);
            setTradeAddPrice(new BigDecimal("0"));
            setWeightedPrice(new BigDecimal("612"));
        }};
        Result<YingOrderConfig> yingOrderConfigResult = client.exchange(configCreateUrl, HttpMethod.POST, new HttpEntity<YingOrderConfig>(config), typeReferenceOrderConfig).getBody();
        if (yingOrderConfigResult.getSuccess()) {
            logger.info("添加核算月配置成功\nPOST {}\nrequest:{}\nresponse:{}", configCreateUrl, printJson(config), printJson(yingOrderConfigResult.getData()));
        } else {
            logger.error("添加核算月配置失败: {}", yingOrderConfigResult.getError());
            System.exit(-2);
        }

        // 2. 修改核算月配置
        YingOrderConfig yingOrderConfig = new YingOrderConfig() {{
            setOrderId(yingOrderResult.getData().getId());
            setId(yingOrderConfigResult.getData().getId());
            setHsMonth("201706");
            setContractBaseInterest(new BigDecimal("0.20"));
            setMaxPrepayRate(new BigDecimal("0.90"));
            setUnInvoicedRate(new BigDecimal("0.7"));
            setExpectHKDays(45);
            setTradeAddPrice(new BigDecimal("0"));
            setWeightedPrice(new BigDecimal("700"));
        }};
        String configUpdateUrl = "/api/ying/" + yingOrderConfigResult.getData().getOrderId() + "/units/" + yingOrderConfigResult.getData().getId();
        Result<Integer> yingOrderConfigUpdateResult = client.exchange(configUpdateUrl, HttpMethod.PUT, new HttpEntity<YingOrderConfig>(yingOrderConfig), typeReferenceInteger).getBody();
        if (yingOrderConfigUpdateResult.getSuccess()) {
            logger.info("更新核算月配置成功\nPUT {}\nrequest = {}\nresponse = {}", configUpdateUrl, printJson(yingOrderConfig), printJson(yingOrderConfigUpdateResult.getData()));
        } else {
            logger.error("更新核算月配置失败: {}", yingOrderConfigUpdateResult.getError());
            System.exit(-2);
        }

        // 3. 核算月分页查询  query by orderId

        Map<String, Object> variablesHs = WebUtils.getUrlVariables(PageYingOrderConfigDTO.class);
        variablesHs.put("orderId", "" + yingOrderResult.getData().getId());
        variablesHs.put("pageSize", 5);
        variablesHs.put("pageNo", 1);
        String configPageUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/units?" + WebUtils.getUrlTemplate(PageYingOrderConfigDTO.class);
        PageResult<YingOrderConfig> yingOrderConfigPageResult = client.exchange(configPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceOrderConfigPage, variablesHs).getBody();
        if (yingOrderConfigPageResult.getSuccess()) {
            logger.info("获取核算月配置分页成功\nGET /api/ying \nrequest: {}\nresponse:\n{}", printJson(variablesHs), printJson(yingOrderConfigPageResult.getData()));
        } else {
            logger.error("获取核算月配置分页失败: {}", yingOrderConfigPageResult.getError());
            System.exit(-1);
        }

        this.yingOrderConfigResult = new Result<YingOrderConfig>(true, yingOrderConfigPageResult.getData().getResults().get(0), null);

        String configDelUrl = "/api/yings/" + yingOrderResult.getData().getId();


    }

    private void fayun() throws JsonProcessingException {

        // 1. 添加发运
        String fayunCreateUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/fayuns";

        YingFayun fayun = new YingFayun() {{
            setOrderId(yingOrderResult.getData().getId());
            setDownstreamCars(166);
            setDownstreamTrafficMode(TrafficMode.MOTOR);
            setUpstreamTrafficMode(TrafficMode.RAIL);
            setUpstreamJHH("1000001");
            setFyAmount(new BigDecimal("1510.60"));
            setArriveStatus(CargoArriveStatus.UNARRIVE);
            setHsId(yingOrderConfigResult.getData().getId());
            setFyDate(stringToTime("2017-6-20"));
        }};

        fayunCreateResult = client.exchange(fayunCreateUrl, HttpMethod.POST, new HttpEntity<>(fayun), typeReferenceFayun).getBody();
        if (fayunCreateResult.getSuccess()) {
            logger.info("创建发运成功\nPOST {}\nrequest = {}\nresponse = {}", fayunCreateUrl, printJson(fayun), printJson(fayunCreateResult.getData()));
        } else {
            logger.info("创建发运失败: {}", fayunCreateResult.getError());
            System.exit(-1);
        }

        // 2. 分页查询

        Map<String, Object> variablesFayun = WebUtils.getUrlVariables(PageYingFayunDTO.class);
        variablesFayun.put("pageSize", 5);
        variablesFayun.put("orderId", yingOrderResult.getData().getId());
        variablesFayun.put("pageNo", 1);

        String fayunPageUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/fayuns?" + WebUtils.getUrlTemplate(PageYingFayunDTO.class);
        PageResult<YingFayun> fayunPageResult = client.exchange(fayunPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceFayunPage, variablesFayun).getBody();
        if (fayunPageResult.getSuccess()) {
            logger.info("发运分页成功\nGET {}\nrequest = {}\nresponse = {}", fayunPageUrl, variablesFayun, printJson(fayunPageResult.getData()));
        } else {
            logger.info("发运分页失败: {}", fayunPageResult.getError());
            System.exit(-1);
        }

        // 3. id查询
        String fayunFindUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/fayuns/" + fayunCreateResult.getData().getId();
        Result<YingFayun> fayunFindResult = client.exchange(fayunFindUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceFayun).getBody();
        if (fayunFindResult.getSuccess()) {
            logger.info("查询发运成功\nGET {}\nrequest = {}\nresponse = {}", fayunFindUrl, "", printJson(fayunFindResult.getData()));
        } else {
            logger.info("查询发运失败: {}", fayunFindResult.getError());
            System.exit(-1);
        }

        // 4. 更新


        YingFayun updateFayun = new YingFayun();
        updateFayun.setOrderId(yingOrderResult.getData().getId());
        updateFayun.setId(fayunCreateResult.getData().getId());
        updateFayun.setArriveStatus(CargoArriveStatus.ARRIVE);

        String fayunUpdateUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/fayuns/" + fayunCreateResult.getData().getId();

        Result<Integer> yingFayunUpdateResult = client.exchange(fayunUpdateUrl, HttpMethod.PUT, new HttpEntity<YingFayun>(updateFayun), typeReferenceInteger).getBody();
        if (yingFayunUpdateResult.getSuccess()) {
            logger.info("发运更新成功\nPUT {}\nrequest = {}\nresponse = {}", fayunUpdateUrl, printJson(fayun), printJson(yingFayunUpdateResult.getData()));
        } else {
            logger.error("发运更新失败: {}", yingFayunUpdateResult.getError());
            System.exit(-2);
        }

        // 测异常!!!
        // 添加发运 - 一致性校验
        // if ( result.getSuccess() == false) {
        //      result.getError().getMessage().equals("adfadsfasdfasasf")
        // } else{
        //      log.error("should fail here");
        //      System.exit(-1);
        // }
        //

    }

    private void huikuan() throws JsonProcessingException {

        // 1. 添加回款
        String huikuanCreateUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/huikuans";
        YingHuikuan huikuan = new YingHuikuan() {{
            setOrderId(yingOrderResult.getData().getId());
            setHsId(yingOrderConfigResult.getData().getId());
            setHuikuanDate(stringToTime("2017-7-28"));
            setHuikuanCompanyId(yingOrderResult.getData().getDownstreamId());
            setHuikuanAmount(new BigDecimal("569968.26"));
//            setHuikuanAmount(new BigDecimal("1000"));
            setHuikuanMode(PayMode.ELEC_REMITTANCE);
            setHuikuanUsage(ReceivePaymentPurpose.PAYMENT_FOR_GOODS);
        }};
        huikuanCreateResult = client.exchange(huikuanCreateUrl, HttpMethod.POST, new HttpEntity<>(huikuan), typeReferenceHuikuan).getBody();
        if (huikuanCreateResult.getSuccess()) {
            logger.info("创建回款成功\nPOST {}\nrequest = {}\nresponse = {}", huikuanCreateUrl, printJson(huikuan), printJson(huikuanCreateResult.getData()));
        } else {
            logger.info("创建回款失败: {}", huikuanCreateResult.getError());
            System.exit(-1);
        }


        // 回款 - 分页

        String huikuanPageUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/huikuans?" + WebUtils.getUrlTemplate(PageYingHuikuanDTO.class);

        Map<String, Object> variablesHuikuan = WebUtils.getUrlVariables(PageYingHuikuanDTO.class);
        variablesHuikuan.put("orderId", yingOrderResult.getData().getId());
        variablesHuikuan.put("pageSize", 5);
        variablesHuikuan.put("pageNo", 1);


        PageResult<YingHuikuan> huikuanPageResult = client.exchange(huikuanPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceHuikuanPage, variablesHuikuan).getBody();

        if (huikuanPageResult.getSuccess()) {
            logger.info("创建分页成功\n GET {}\nrequest = {}\nresponse = {}", huikuanPageUrl, "", printJson(huikuanPageResult.getData()));
        } else {
            logger.info("创建分页失败: {}", huikuanPageResult.getError());
            System.exit(-1);
        }

        // 3. id查询
        String huikuanFindUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/huikuans/" + huikuanCreateResult.getData().getId();
        Result<YingHuikuan> huikuanFindResult = client.exchange(huikuanFindUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceHuikuan).getBody();
        if (huikuanFindResult.getSuccess()) {
            logger.info("查询回款成功\nGET {}\nrequest = {}\nresponse = {}", huikuanFindUrl, "", printJson(huikuanFindResult.getData()));
        } else {
            logger.info("查询回款失败: {}", huikuanFindResult.getError());
            System.exit(-1);
        }

        // 4. 更新 todo
    }

    private void huankuan() throws JsonProcessingException {
        // 1. 添加还款
        String huankuanCreateUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/huankuans";
        YingHuankuan huankuan = new YingHuankuan() {{
            setOrderId(yingOrderResult.getData().getId());
            setHsId(yingOrderConfigResult.getData().getId());
            setSkCompanyId(yingOrderResult.getData().getOrderPartyList().get(0).getCustomerId());
            setHuankuankDate(stringToTime("2017-7-26"));
            setHuankuanAmount(new BigDecimal("511700.02"));
            setHuankuanFee(new BigDecimal("560"));
            setHuankuanInterest(new BigDecimal("1700.02"));

        }};
        huankuanCreateResult = client.exchange(huankuanCreateUrl, HttpMethod.POST, new HttpEntity<>(huankuan), typeReferenceHuankuan).getBody();
        if (huankuanCreateResult.getSuccess()) {
            logger.info("创建还款成功\nPOST {}\nrequest = {}\nresponse = {}", huankuanCreateUrl, printJson(huankuan), printJson(huankuanCreateResult.getData()));
        } else {
            logger.info("创建还款失败: {}", huankuanCreateResult.getError());
            System.exit(-1);
        }

        // 2. 分页
        String huankuanPageUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/huankuans?" + WebUtils.getUrlTemplate(PageYingHuankuanDTO.class);
        Map<String, Object> variableshuankuan = WebUtils.getUrlVariables(PageYingHuankuanDTO.class);
        variableshuankuan.put("orderId", yingOrderResult.getData().getId());
        variableshuankuan.put("pageSize", 5);
        variableshuankuan.put("pageNo", 1);

        PageResult<YingHuankuan> huankuanPageResult = client.exchange(huankuanPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceHuankuanPage, variableshuankuan).getBody();
        if (huankuanPageResult.getSuccess()) {
            logger.info("还款  分页成功\n GET {}\nrequest = {}\nresponse = {}", huankuanPageUrl, "", printJson(huankuanPageResult.getData()));
        } else {
            logger.info("还款  分页失败: {}", huankuanPageResult.getError());
            System.exit(-1);
        }

        // 3. id查询
        String huankuanFindUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/huankuans/" + huankuanCreateResult.getData().getId();
        Result<YingHuankuan> huankuanFindResult = client.exchange(huankuanFindUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceHuankuan).getBody();
        if (huankuanFindResult.getSuccess()) {
            logger.info("查询  还款成功\n  GET {}\nrequest = {}\nresponse = {}", huankuanFindUrl, "", printJson(huankuanFindResult.getData()));
        } else {
            logger.info("查询  还款失败: {}", huankuanFindResult.getError());
            System.exit(-1);
        }

        // 4. 更新 todo  timestamp
    }

    private void fukuan() throws JsonProcessingException {
        // 1. 添加付款
        String fukuanCreateUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/fukuans";
        YingFukuan yingFukuan = new YingFukuan() {{
            setOrderId(yingOrderResult.getData().getId());
            setHsId(yingOrderConfigResult.getData().getId());
            setPayDate(stringToTime("2017-7-6"));
            setReceiveCompanyId(yingOrderResult.getData().getUpstreamId());
            setPayUsage(PaymentPurpose.DEPOSITECASH);
            setPayMode(PayMode.ELEC_REMITTANCE);
            setPayAmount(new BigDecimal("510000"));
            setUseDays(35);
            setUseInterest(new BigDecimal("0.075"));
            setCapitalId(yingOrderResult.getData().getOrderPartyList().get(0).getCustomerId());
        }};
        YingFukuan yingFukuantwo = new YingFukuan() {{
            setOrderId(yingOrderResult.getData().getId());
            setHsId(yingOrderConfigResult.getData().getId());
            setPayDate(stringToTime("2017-8-10"));
            setReceiveCompanyId(yingOrderResult.getData().getUpstreamId());
            setPayUsage(PaymentPurpose.FIAL_PAYMENT);
            setPayMode(PayMode.ELEC_REMITTANCE);
            setPayAmount(new BigDecimal("54294.93"));
            setCapitalId(yingOrderResult.getData().getMainAccounting());
        }};
        fukuanResult = client.exchange(fukuanCreateUrl, HttpMethod.POST, new HttpEntity<>(yingFukuan), typeReferenceFukuan).getBody();
        if (fukuanResult.getSuccess()) {
            logger.info("创建付款成功\nPOST {}\nrequest = {}\nresponse = {}", fukuanCreateUrl, printJson(yingFukuan), printJson(fukuanResult.getData()));
        } else {
            logger.info("创建付款失败: {}", fukuanResult.getError());
            System.exit(-1);
        }
        client.exchange(fukuanCreateUrl, HttpMethod.POST, new HttpEntity<>(yingFukuantwo), typeReferenceFukuan).getBody();
        yingFukuantwo.setPayAmount(new BigDecimal("5680"));

        client.exchange(fukuanCreateUrl, HttpMethod.POST, new HttpEntity<>(yingFukuantwo), typeReferenceFukuan).getBody();
        // 2. 分页
        String fukuanPageUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/fukuans?" + WebUtils.getUrlTemplate(PageYingFukuanDTO.class);

        Map<String, Object> fukuanVariablesPage = WebUtils.getUrlVariables(PageYingFukuanDTO.class);
        fukuanVariablesPage.put("orderId", yingOrderResult.getData().getId());
        fukuanVariablesPage.put("pageSize", 5);
        fukuanVariablesPage.put("pageNo", 1);

        PageResult<YingFukuan> fukuanPageResult = client.exchange(fukuanPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceFukuanPage, fukuanVariablesPage).getBody();
        if (fukuanPageResult.getSuccess()) {
            logger.info("付款分页成功\nGET {}\nrequest = {}\nresponse = {}", fukuanPageUrl, "", printJson(fukuanPageResult.getData()));
        } else {
            logger.info("付款分页失败: {}", fukuanPageResult.getError());
            System.exit(-1);
        }

        // 3. id查询
        String fukuanFindUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/fukuans/" + fukuanResult.getData().getId();
        Result<YingFukuan> fukuanFindResult = client.exchange(fukuanFindUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceFukuan).getBody();
        if (fukuanFindResult.getSuccess()) {
            logger.info("查询付款成功\nGET {}\nrequest = {}\nresponse = {}", fukuanFindUrl, "", printJson(fukuanFindResult.getData()));
        } else {
            logger.info("查询付款失败: {}", fukuanFindResult.getError());
            System.exit(-1);
        }

        // 4. 更新 todo

    }

    private void upstream() throws JsonProcessingException {


        // 1. 添加上游结算

        String upstreamCreateUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/settleupstream";
        YingSettleUpstream upstream = new YingSettleUpstream() {
            {
                setOrderId(yingOrderResult.getData().getId());
                setHsId(yingOrderConfigResult.getData().getId());
                setSettleDate(stringToTime("2017-8-4"));
                setMoney(new BigDecimal("565994.59"));
                setDiscountType(DiscountMode.NO_DISCOUNT);
                setAmount(new BigDecimal("1510.61"));
            }
        };

        upstreamCreateResult = client.exchange(upstreamCreateUrl, HttpMethod.POST, new HttpEntity<>(upstream), typeReferenceSettleUpstream).getBody();
        if (upstreamCreateResult.getSuccess()) {
            logger.info("创建上游结算成功\nPOST {}\nrequest = {}\nresponse = {}", upstreamCreateUrl, printJson(upstream), printJson(upstreamCreateResult.getData()));
        } else {
            logger.info("创建上游结算失败: {}", upstreamCreateResult.getError());
            System.exit(-1);
        }

        // 2. 分页
        String upstreamPageUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/settleupstream?" + WebUtils.getUrlTemplate(PageYingSettleUpstreamDTO.class);

        Map<String, Object> upstreamVariables = WebUtils.getUrlVariables(PageYingSettleUpstreamDTO.class);
        upstreamVariables.put("orderId", yingOrderResult.getData().getId());
        upstreamVariables.put("pageSize", 5);
        upstreamVariables.put("pageNo", 1);

        PageResult<YingSettleUpstream> upstreamPageResult = client.exchange(upstreamPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceSettleUpstreamPage, upstreamVariables).getBody();
        if (upstreamPageResult.getSuccess()) {
            logger.info("创建分页成功\nGET {}\nrequest = {}\nresponse = {}", upstreamPageUrl, "", printJson(upstreamPageResult.getData()));
        } else {
            logger.info("创建分页失败: {}", upstreamPageResult.getError());
            System.exit(-1);
        }

        // 3. id 查询
        String upstreamFindUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/settleupstream/" + upstreamCreateResult.getData().getId();
        Result<YingSettleUpstream> upstreamFindResult = client.exchange(upstreamFindUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceSettleUpstream).getBody();
        if (upstreamFindResult.getSuccess()) {
            logger.info("上游结算成功\nGET {}\nrequest = {}\nresponse = {}", upstreamFindUrl, "", printJson(upstreamFindResult.getData()));
        } else {
            logger.info("上游结算失败: {}", upstreamFindResult.getError());
            System.exit(-1);
        }

        // 4. 更新
        String fayunUpdateUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/settleupstream/" + upstreamCreateResult.getData().getId();
        upstream.setAmount(new BigDecimal("9999"));
        upstream.setId(upstreamCreateResult.getData().getId());
        Result<Integer> upstreamUpdateResult = client.exchange(fayunUpdateUrl, HttpMethod.PUT, new HttpEntity<YingSettleUpstream>(upstream), typeReferenceInteger).getBody();
        if (upstreamUpdateResult.getSuccess()) {
            logger.info("更新上游结算成功\nPOST {}\nrequest = {}\nresponse = {}", fayunUpdateUrl, printJson(upstream), printJson(upstreamUpdateResult.getData()));
        } else {
            logger.error("更新上游结算失败: {}", upstreamUpdateResult.getError());
            System.exit(-2);
        }
    }

    private void downstream() throws JsonProcessingException {

        // 1. 添加下游结算
        String downstreamCreateUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/settledownstream";
        YingSettleDownstream downstream = new YingSettleDownstream(
        ) {{
            setAmount(new BigDecimal("1510.61"));
            setMoney(new BigDecimal("55968.26"));
            setHsId(yingOrderConfigResult.getData().getId());
            setOrderId(yingOrderResult.getData().getId());
            setSettleDate(stringToTime("2017-07-07"));

            setSettleGap(new BigDecimal("0"));
        }};
        downstreamCreateResult = client.exchange(downstreamCreateUrl, HttpMethod.POST, new HttpEntity<>(downstream), typeReferenceSettleDownstream).getBody();
        if (downstreamCreateResult.getSuccess()) {
            logger.info("创建下游结算成功\nPOST {}\nrequest = {}\nresponse = {}", downstreamCreateUrl, printJson(downstream), printJson(downstreamCreateResult.getData()));
        } else {
            logger.info("创建下游结算失败: {}", downstreamCreateResult.getError());
            System.exit(-1);
        }

        // 2. 分页
        String downstreamPageUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/settledownstream?" + WebUtils.getUrlTemplate(PageYingSettleDownstreamDTO.class);
        Map<String, Object> downstreamVariables = WebUtils.getUrlVariables(PageYingSettleDownstreamDTO.class);
        downstreamVariables.put("orderId", yingOrderResult.getData().getId());
        downstreamVariables.put("pageSize", 5);
        downstreamVariables.put("pageNo", 1);


        PageResult<YingSettleDownstream> downstreamPageResult = client.exchange(downstreamPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceSettleDownstreamPage, downstreamVariables).getBody();
        if (downstreamPageResult.getSuccess()) {
            logger.info("下游结算分页成功\nGET {}\nrequest = {}\nresponse = {}", downstreamPageUrl, "", printJson(downstreamPageResult.getData()));
        } else {
            logger.info("下游结算分页失败: {}", downstreamPageResult.getError());
            System.exit(-1);
        }

        // 3. id查询

        String downstreamFindUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/settledownstream/" + downstreamCreateResult.getData().getId();
        Result<YingSettleDownstream> downstreamFindResult = client.exchange(downstreamFindUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceSettleDownstream).getBody();
        if (downstreamFindResult.getSuccess()) {
            logger.info("下游结算查询成功\n Get {}\nrequest = {}\nresponse = {}", downstreamFindUrl, "", printJson(downstreamFindResult.getData()));
        } else {
            logger.info("下游结算查询失败: {}", downstreamFindResult.getError());
            System.exit(-1);
        }

        // 4. 更新 todo

    }

    //此订单无运输方结算
    private void traffic() throws JsonProcessingException {

        // 1. 添加运输方结算
        String trafficCreateUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/settletraffic";
        YingSettleTraffic traffic = new YingSettleTraffic() {{
            setHsId(yingOrderConfigResult.getData().getId());
            setOrderId(yingOrderResult.getData().getId());
            setMoney(new BigDecimal("1000"));
            setAmount(new BigDecimal("100"));
            setSettleDate(LocalDateTime.now());
            setTrafficCompanyId(yingOrderResult.getData().getMainAccounting());

        }};
        trafficCreateResult = client.exchange(trafficCreateUrl, HttpMethod.POST, new HttpEntity<>(traffic), typeReferenceSettleTraffic).getBody();
        if (trafficCreateResult.getSuccess()) {
            logger.info("创建运输方结算成功\n POST {}\nrequest = {}\nresponse = {}", trafficCreateUrl, printJson(traffic), printJson(trafficCreateResult.getData()));
        } else {
            logger.info("创建运输方结算失败: {}", trafficCreateResult.getError());
            System.exit(-1);
        }

        // 2. 分页
        String trafficPageUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/settletraffic?" + WebUtils.getUrlTemplate(PageYingTransferDTO.class);

        Map<String, Object> trafficVariables = WebUtils.getUrlVariables(PageYingTransferDTO.class);
        trafficVariables.put("orderId", yingOrderResult.getData().getId());
        trafficVariables.put("pageSize", 5);
        trafficVariables.put("pageNo", 1);


        PageYingSettleTrafficDTO trafficDTO = new PageYingSettleTrafficDTO();
        trafficDTO.setOrderId(yingOrderResult.getData().getId());
        PageResult<YingSettleTraffic> trafficPageResult = client.exchange(trafficPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceSettleTrafficPage, trafficVariables).getBody();
        if (trafficPageResult.getSuccess()) {
            logger.info("运输方结算分页成功\nGET {}\nrequest = {}\nresponse = {}", trafficPageUrl, "", printJson(trafficPageResult.getData()));
        } else {
            logger.info("运输方结算分页失败: {}", trafficPageResult.getError());
            System.exit(-1);
        }

        // 3. id查询
        String trafficFindUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/settletraffic/" + trafficCreateResult.getData().getId();
        Result<YingSettleTraffic> trafficFindResult = client.exchange(trafficFindUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceSettleTraffic).getBody();
        if (trafficFindResult.getSuccess()) {
            logger.info("查询运输方结算成功\nGET {}\nrequest = {}\nresponse = {}", trafficFindUrl, "", printJson(trafficFindResult.getData()));
        } else {
            logger.info("查询运输方结算失败: {}", trafficFindResult.getError());
            System.exit(-1);
        }

        // 4. 更新
        String fayunUpdateUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/settletraffic/" + trafficCreateResult.getData().getId();
        traffic.setAmount(new BigDecimal("9999"));
        traffic.setOrderId(yingOrderResult.getData().getId());
        traffic.setId(trafficCreateResult.getData().getId());
        Result<Integer> yingFayunUpdateResult = client.exchange(fayunUpdateUrl, HttpMethod.PUT, new HttpEntity<YingSettleTraffic>(traffic), typeReferenceInteger).getBody();
        if (yingFayunUpdateResult.getSuccess()) {
            logger.info("更新输方结算成功\nPOST {}\nrequest = {}\nresponse = {}", fayunUpdateUrl, printJson(traffic), printJson(yingFayunUpdateResult.getData()));
        } else {
            logger.error("更新输方结算失败: {}", yingFayunUpdateResult.getError());
            System.exit(-2);
        }
    }

    private void fee() throws JsonProcessingException {
        // 1. 添加费用
        String feeCreateUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/fees";
        YingFee fee = new YingFee() {{
            setHsId(yingOrderConfigResult.getData().getId());
            setOrderId(yingOrderResult.getData().getId());
            setAmount(new BigDecimal("100"));
            setName(FeeClass.SERVICE_FEE);
        }};
        Result<YingFee> feeCreateResult = client.exchange(feeCreateUrl, HttpMethod.POST, new HttpEntity<>(fee), typeReferenceFee).getBody();
        if (feeCreateResult.getSuccess()) {
            logger.info("创建费用成功\nPOST {}\nrequest = {}\nresponse = {}", feeCreateUrl, printJson(fee), printJson(feeCreateResult.getData()));
        } else {
            logger.info("创建费用失败: {}", feeCreateResult.getError());
            System.exit(-1);
        }


        // 2. 分页
        String feePageUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/fees?" + WebUtils.getUrlTemplate(PageYingFeeDTO.class);

        Map<String, Object> feeVariables = WebUtils.getUrlVariables(PageYingFeeDTO.class);
        feeVariables.put("orderId", yingOrderResult.getData().getId());
        feeVariables.put("pageSize", 5);
        feeVariables.put("pageNo", 1);

        PageResult<YingFee> feePageResult = client.exchange(feePageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceFeePage, feeVariables).getBody();
        if (feePageResult.getSuccess()) {
            logger.info("费用分页查询成功\nGET {}\nrequest = {}\nresponse = {}", feePageUrl, "", printJson(feePageResult.getData()));
        } else {
            logger.info("费用分页查询失败: {}", feePageResult.getError());
            System.exit(-1);
        }

        // 3. 查询
        String feeFindUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/fees/" + feeCreateResult.getData().getId();
        feeFindResult = client.exchange(feeFindUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceFee).getBody();
        if (feeFindResult.getSuccess()) {
            logger.info("费用查询成功\nGET {}\nrequest = {}\nresponse = {}", feeFindUrl, "", printJson(feeFindResult.getData()));
        } else {
            logger.info("费用查询失败: {}", feeFindResult.getError());
            System.exit(-1);
        }

        // 4. 更新
        String fayunUpdateUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/fees/" + feeFindResult.getData().getId();
        fee.setAmount(new BigDecimal("9999"));
        fee.setOrderId(yingOrderResult.getData().getId());
        fee.setId(feeCreateResult.getData().getId());
        Result<Integer> yingFayunUpdateResult = client.exchange(fayunUpdateUrl, HttpMethod.PUT, new HttpEntity<YingFee>(fee), typeReferenceInteger).getBody();
        if (yingFayunUpdateResult.getSuccess()) {
            logger.info("更新费用成功\nPUT {}\nrequest = {}\nresponse = {}", fayunUpdateUrl, printJson(fee), printJson(yingFayunUpdateResult.getData()));
        } else {
            logger.error("更新费用失败: {}", yingFayunUpdateResult.getError());
            System.exit(-2);
        }
    }

    private List<YingInvoiceDetail> invoiceDetail() {
        List<YingInvoiceDetail> listVoiceDetail = Lists.newArrayList(
                new YingInvoiceDetail() {{
                    setInvoiceNumber("66661234");
                    setCargoAmount(new BigDecimal("10000"));
                    setTaxRate(new BigDecimal("0.17"));
                    setPriceAndTax(new BigDecimal("340000"));
                }},
                new YingInvoiceDetail() {{
                    setInvoiceNumber("66661235");
                    setCargoAmount(new BigDecimal("367"));
                    setTaxRate(new BigDecimal("0.17"));
                    setPriceAndTax(new BigDecimal("170000"));
                }}

        );
        return listVoiceDetail;

    }


    private void invoice() throws JsonProcessingException {

        // 1. 添加发票发票
        String invoiceCreateUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/invoices";
        YingInvoice yingInvoice = new YingInvoice(
                null,
                yingOrderResult.getData().getId(),
                yingOrderConfigResult.getData().getId(),
                InvoiceDirection.INCOME,
                InvoiceType.GOODS_INVOICE,
                stringToTime("2017-7-6"),
                yingOrderResult.getData().getUpstreamId(),
                yingOrderResult.getData().getMainAccounting(),
                null,
                invoiceDetail()
        );
        YingInvoice yingInvoiceSecond = new YingInvoice(
                null,
                yingOrderResult.getData().getId(),
                yingOrderConfigResult.getData().getId(),
                InvoiceDirection.INCOME,
                InvoiceType.GOODS_INVOICE,
                stringToTime("2017-8-4"),
                yingOrderResult.getData().getUpstreamId(),
                yingOrderResult.getData().getMainAccounting(),
                null,
                Lists.newArrayList(
                        new YingInvoiceDetail() {{
                            setInvoiceNumber("66661236");
                            setCargoAmount(new BigDecimal("143.67"));
                            setTaxRate(new BigDecimal("0.17"));
                            setPriceAndTax(new BigDecimal("565994.5995"));
                        }})
        );
        invoiceCreateResult = client.exchange(invoiceCreateUrl, HttpMethod.POST, new HttpEntity<YingInvoice>(yingInvoice), typeReferenceInvoice).getBody();
        if (invoiceCreateResult.getSuccess()) {
            logger.info("创建发票成功\nPOST {}\nrequest = {}\nresponse = {}", invoiceCreateUrl, printJson(yingInvoice), printJson(invoiceCreateResult.getData()));
        } else {
            logger.info("创建发票失败: {}", invoiceCreateResult.getError());
            System.exit(-1);
        }

        //第二笔发票记录
        client.exchange(invoiceCreateUrl, HttpMethod.POST, new HttpEntity<YingInvoice>(yingInvoiceSecond), typeReferenceInvoice).getBody();

//        // 发票 - 分页

        // 2. 分页
        String invoicePageUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/invoices?" + WebUtils.getUrlTemplate(PageYingInvoiceDTO.class);

        Map<String, Object> invoiceVariables = WebUtils.getUrlVariables(PageYingInvoiceDTO.class);
        invoiceVariables.put("orderId", yingOrderResult.getData().getId());
        invoiceVariables.put("pageSize", 5);
        invoiceVariables.put("pageNo", 1);
        invoiceVariables.put("invoiceType", InvoiceType.GOODS_INVOICE);

        PageResult<YingInvoice> invoicePageResult = client.exchange(invoicePageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceInvoicePage, invoiceVariables).getBody();
        if (invoicePageResult.getSuccess()) {
            logger.info("创建分页成功\nGET {}\nrequest = {}\nresponse = {}", invoicePageUrl, "", printJson(invoicePageResult.getData()));
        } else {
            logger.info("创建分页失败: {}", invoicePageResult.getError());
            System.exit(-1);
        }

        // 3. id查询
        String invoiceFindUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/invoices" + "/" + invoiceCreateResult.getData().getId();
        Result<YingInvoice> invoiceFindResult = client.exchange(invoiceFindUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceInvoice).getBody();
        if (invoiceFindResult.getSuccess()) {
            logger.info("查询发票成功\nGET {}\nrequest = {}\nresponse = {}", invoiceFindUrl, "", printJson(invoiceFindResult.getData()));
        } else {
            logger.info("查询发票失败: {}", invoiceFindResult.getError());
            System.exit(-1);
        }

        // 4. 更新
        String invoiceUpdateUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/invoices/" + invoiceCreateResult.getData().getId();
        yingInvoice.setId(invoiceCreateResult.getData().getId());
        yingInvoice.setInvoiceDirection(InvoiceDirection.INCOME);
        Result<Integer> invoiceUpdateResult = client.exchange(invoiceFindUrl, HttpMethod.PUT, new HttpEntity<YingInvoice>(yingInvoice), typeReferenceInteger).getBody();
        if (invoiceFindResult.getSuccess()) {
            logger.info("更新发票成功\nPUT {}\nrequest = {}\nresponse = {}", invoiceUpdateUrl, printJson(yingInvoice), printJson(invoiceUpdateResult.getData()));
        } else {
            logger.info("更新发票失败: {}", invoiceUpdateResult.getError());
            System.exit(-1);
        }

//        deleteTest();
    }

    Result<YingInvoice> invoiceCreateResult = null;
    Result<YingFee> feeFindResult = null;
    Result<YingSettleTraffic> trafficCreateResult = null;
    Result<YingSettleDownstream> downstreamCreateResult = null;
    Result<YingSettleUpstream> upstreamCreateResult = null;
    Result<YingFukuan> fukuanResult = null;
    Result<YingHuankuan> huankuanCreateResult = null;
    Result<YingHuikuan> huikuanCreateResult = null;
    Result<YingFayun> fayunCreateResult = null;

    public void deleteTest() throws JsonProcessingException {
//        //发票删除
//        String invoiceDelUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/invoices/" + invoiceCreateResult.getData().getId();
//        Result<Integer> yingnvoiceDelResult = client.exchange(invoiceDelUrl, HttpMethod.DELETE,  HttpEntity.EMPTY, typeReferenceInteger).getBody();
//        if (yingnvoiceDelResult.getSuccess()) {
//            logger.info("发票删除成功\n DELETE {}\nrequest = {}\nresponse = {}", invoiceDelUrl, "", printJson(yingnvoiceDelResult.getData()));
//        } else {
//            logger.error("发票删除失败: {}", yingnvoiceDelResult.getError());
//            System.exit(-2);
//        }


        //费用删除
        String feeDelUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/fees/" + feeFindResult.getData().getId();

        Result<Integer> yingfeeDelResult = client.exchange(feeDelUrl, HttpMethod.DELETE, HttpEntity.EMPTY, typeReferenceInteger).getBody();
        if (yingfeeDelResult.getSuccess()) {
            logger.info("费用删除成功\n DELETE {}\nrequest = {}\nresponse = {}", feeDelUrl, "", printJson(yingfeeDelResult.getData()));
        } else {
            logger.error("费用删除失败: {}", yingfeeDelResult.getError());
            System.exit(-2);
        }
        //运输发结算删除
        String settletrafficDeleteUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/settletraffic/" + trafficCreateResult.getData().getId();

        Result<Integer> settletrafficDelResult = client.exchange(settletrafficDeleteUrl, HttpMethod.DELETE, HttpEntity.EMPTY, typeReferenceInteger).getBody();
        if (settletrafficDelResult.getSuccess()) {
            logger.info("运输发结算删除成功\n DELETE {}\nrequest = {}\nresponse = {}", settletrafficDeleteUrl, "", printJson(settletrafficDelResult.getData()));
        } else {
            logger.error("运输发结算删除失败: {}", settletrafficDelResult.getError());
            System.exit(-2);
        }
        //下游结算
        String downstreamDeleteUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/settledownstream/" + downstreamCreateResult.getData().getId();

        Result<Integer> downstreamDelResult = client.exchange(downstreamDeleteUrl, HttpMethod.DELETE, HttpEntity.EMPTY, typeReferenceInteger).getBody();
        if (downstreamDelResult.getSuccess()) {
            logger.info("下游结算删除成功\n DELETE {}\nrequest = {}\nresponse = {}", downstreamDeleteUrl, "", printJson(downstreamDelResult.getData()));
        } else {
            logger.error("下游结算删除失败: {}", downstreamDelResult.getError());
            System.exit(-2);
        }
        //上游结算
        String upstreamDeleteUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/settleupstream/" + upstreamCreateResult.getData().getId();


        Result<Integer> upstreamDelResult = client.exchange(upstreamDeleteUrl, HttpMethod.DELETE, HttpEntity.EMPTY, typeReferenceInteger).getBody();
        if (upstreamDelResult.getSuccess()) {
            logger.info("上游结算删除成功\n DELETE {}\nrequest = {}\nresponse = {}", upstreamDeleteUrl, "", printJson(upstreamDelResult.getData()));
        } else {
            logger.error("上游结算删除失败: {}", upstreamDelResult.getError());
            System.exit(-2);
        }
        //付款删除
        String fukuanDeleteUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/fukuans/" + fukuanResult.getData().getId();

        Result<Integer> fukuanDelResult = client.exchange(fukuanDeleteUrl, HttpMethod.DELETE, HttpEntity.EMPTY, typeReferenceInteger).getBody();
        if (fukuanDelResult.getSuccess()) {
            logger.info("付款删除成功\n DELETE {}\nrequest = {}\nresponse = {}", fukuanDeleteUrl, "", printJson(fukuanDelResult.getData()));
        } else {
            logger.error("付款删除失败: {}", fukuanDelResult.getError());
            System.exit(-2);
        }
        //回款删除
        String huikuanDeleteUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/huikuans/" + huikuanCreateResult.getData().getId();

        Result<Integer> huikuanDelResult = client.exchange(huikuanDeleteUrl, HttpMethod.DELETE, HttpEntity.EMPTY, typeReferenceInteger).getBody();
        if (huikuanDelResult.getSuccess()) {
            logger.info("回款删除成功\n DELETE {}\nrequest = {}\nresponse = {}", huikuanDeleteUrl, "", printJson(huikuanDelResult.getData()));
        } else {
            logger.error("回款删除失败: {}", huikuanDelResult.getError());
            System.exit(-2);
        }
        //还款删除
        String huankuanDeleteUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/huankuans/" + huankuanCreateResult.getData().getId();

        Result<Integer> huankuanDelResult = client.exchange(huankuanDeleteUrl, HttpMethod.DELETE, HttpEntity.EMPTY, typeReferenceInteger).getBody();
        if (huankuanDelResult.getSuccess()) {
            logger.info("还款删除成功\n DELETE {}\nrequest = {}\nresponse = {}", huankuanDeleteUrl, "", printJson(huankuanDelResult.getData()));
        } else {
            logger.error("还款删除失败: {}", huankuanDelResult.getError());
            System.exit(-2);
        }
        //发运删除

        String fayunDeleteUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/fayuns/" + fayunCreateResult.getData().getId();


        Result<Integer> fayunDelResult = client.exchange(fayunDeleteUrl, HttpMethod.DELETE, HttpEntity.EMPTY, typeReferenceInteger).getBody();
        if (fayunDelResult.getSuccess()) {
            logger.info("发运删除成功\n DELETE {}\nrequest = {}\nresponse = {}", fayunDeleteUrl, "", printJson(fayunDelResult.getData()));
        } else {
            logger.error("发运删除失败: {}", fayunDelResult.getError());
            System.exit(-2);
        }
        //核算月配置删除
//        String configDeleteUrl = "/api/ying/" + yingOrderConfigResult.getData().getOrderId() + "/units/" + yingOrderConfigResult.getData().getId();
//
//        Result<Integer> configDelResult = client.exchange(configDeleteUrl, HttpMethod.DELETE, HttpEntity.EMPTY, typeReferenceInteger).getBody();
//        if (configDelResult.getSuccess()) {
//            logger.info("核算月配置删除成功\n DELETE {}\nrequest = {}\nresponse = {}", configDeleteUrl, "", printJson(configDelResult.getData()));
//        } else {
//            logger.error("核算月配置删除失败: {}", configDelResult.getError());
//            System.exit(-2);
//        }
        //订单删除
        String orderDelUrl = "/api/yings/" + yingOrderResult.getData().getId();
        Result<Integer> yingOrderDelResult = client.exchange(orderDelUrl, HttpMethod.DELETE, HttpEntity.EMPTY, typeReferenceInteger).getBody();
        if (yingOrderDelResult.getSuccess()) {
            logger.info("订单删除成功\n DELETE {}\nrequest = {}\nresponse = {}", orderDelUrl, "", printJson(yingOrderDelResult.getData()));
        } else {
            logger.error("订单删除失败: {}", yingOrderDelResult.getError());
            System.exit(-2);
        }
        //用户删除


    }


    /**
     * @param strData 格式Y
     * @return
     */
    public LocalDateTime stringToTime(String strData) {
        try {
            String[] data = strData.split("-");
            return LocalDateTime.of(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), 0, 0, 0);
        } catch (Exception e) {
            return LocalDateTime.now();
        }
    }


}