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
import com.yimei.hs.util.WebUtils;
import com.yimei.hs.ying.dto.*;
import com.yimei.hs.ying.entity.*;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    List<Long> partyIds = null;
    Result<User> userResult = null;
    Result<YingOrder> yingOrderResult = null;
    Result<YingOrderConfig> yingOrderConfigResult = null;

    @Test
    public void initUser() {
        createUser("13022117050", "123456");
    }

    @Test
    public void userTest() throws JsonProcessingException {
        user();
        order();
        config();
//        fayun();
        fukuan();
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
        updateUser.setIsActive(true);
        updateUser.setDeptId(userResult.getData().getDeptId());
        String updateUserURL = "/api/users/" + userResult.getData().getId();
        Result<Integer> updateUserRtn = client.exchange(updateUserURL, HttpMethod.PUT, new HttpEntity<User>(updateUser), typeReferenceInteger).getBody();
        if (updateUserRtn.getSuccess()) {
            logger.info("更新用户成功\nPUT {}\nrequest = {}\nresponse = {}", "/api/users", updateUserURL, printJson(updateUser), printJson(updateUserRtn.getData()));
        } else {
            logger.error("更新用户失败: {}", updateUserRtn.getError());
            System.exit(-2);
        }

        // 6. 用户登录
        // Result<String> loginResult = post("/api/login", newUser, String.class);
        Result<String> loginResult = client.exchange("/api/login", HttpMethod.POST, new HttpEntity<User>(newUser), typeReferenceString).getBody();
        if (loginResult.getSuccess()) {
            logger.info("登录成功\nPOST {}\nrequest = {}\nresponse = {}", "/api/login", printJson(newUser), printJson(loginResult.getData()));
        } else {
            logger.error("登录失败: {}", loginResult.getError());
        }
        setClientInterceptor(loginResult.getData());
    }

    private void order() throws JsonProcessingException {

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
        yingOrderResult = client.exchange("/api/yings", HttpMethod.POST, new HttpEntity<YingOrder>(yingOrder), typeReferenceOrder).getBody();
        if (yingOrderResult.getSuccess()) {
            logger.info("创建应收订单成功\nPOST {}\nrequest = {}\nresponse = {}", "/api/yings", printJson(yingOrder), printJson(yingOrderResult.getData()));
        } else {
            logger.error("创建应收订单失败: {}", yingOrderResult.getError());
        }

        // 8. 分页查询order
        Map<String, Object> variables = new HashMap<>();
        variables.put("ownerId", userResult.getData().getId());
        String orderPageUrl = "/api/yings?" + WebUtils.getUrlTemplate(PageYingOrderDTO.class);
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
            logger.error("查询订单失败: {}", yingOrderResult1.getError());
            System.exit(-1);
        }


    }

    private void config() throws JsonProcessingException {

        // 1. 增加核算月配置
        String configCreateUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/configs";

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
            setHsMonth("201711");
            setContractBaseInterest(new BigDecimal("0.20"));
            setMaxPrepayRate(new BigDecimal("0.90"));
            setUnInvoicedRate(new BigDecimal("0.7"));
            setExpectHKDays(45);
            setTradeAddPrice(new BigDecimal("0"));
            setWeightedPrice(new BigDecimal("700"));
        }};
        String configUpdateUrl = "/api/ying/" + yingOrderConfigResult.getData().getOrderId() + "/configs/" + yingOrderConfigResult.getData().getId();
        Result<Integer> yingOrderConfigUpdateResult = client.exchange(configUpdateUrl, HttpMethod.PUT, new HttpEntity<YingOrderConfig>(yingOrderConfig), typeReferenceInteger).getBody();
        if (yingOrderConfigUpdateResult.getSuccess()) {
            logger.info("更新核算月配置成功\nPOST {}\nrequest = {}\nresponse = {}", configUpdateUrl, printJson(yingOrderConfig), printJson(yingOrderConfigUpdateResult.getData()));
        } else {
            logger.error("更新核算月配置失败: {}", yingOrderConfigUpdateResult.getError());
            System.exit(-2);
        }

        // 3. 核算月分页查询  query by orderId
        Map<String, Object> variablesHs = new HashMap<>();
        variablesHs.put("orderId", "" + yingOrderResult.getData().getId());
        PageYingOrderConfigDTO configDTO = new PageYingOrderConfigDTO();
        configDTO.setOrderId(yingOrderResult.getData().getId());

        String configPageUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/configs";
        PageResult<YingOrderConfig> yingOrderConfigPageResult = client.exchange(configPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceOrderConfigPage, variablesHs).getBody();
        if (yingOrderConfigPageResult.getSuccess()) {
            logger.info("获取核算月配置分页成功 GET /api/yings request: {}\nresponse:\n{}", variablesHs, printJson(yingOrderConfigPageResult.getData()));
        } else {
            logger.error("获取核算月配置分页失败: {}", yingOrderConfigPageResult.getError());
            System.exit(-1);
        }

        this.yingOrderConfigResult = new Result<YingOrderConfig>(true, yingOrderConfigPageResult.getData().getResults().get(0), null);
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
            setArriveStatus(CargoArriveStatus.ARRIVE);
            setHsId(yingOrderConfigResult.getData().getId());
            setFyDate(stringToTime("2017-6-20"));
        }};
        Result<YingFayun> fayunCreateResult = client.exchange(fayunCreateUrl, HttpMethod.POST, new HttpEntity<>(fayun), typeReferenceFayun).getBody();
        if (fayunCreateResult.getSuccess()) {
            logger.info("创建发运成功\nPOST {}\nrequest = {}\nresponse = {}", fayunCreateUrl, printJson(fayun), printJson(fayunCreateResult.getData()));
        } else {
            logger.info("创建发运失败: {}", fayunCreateResult.getError());
            System.exit(-1);
        }

        // 2. 分页查询
        String fayunPageUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/fayuns";
        PageResult<YingFayun> fayunPageResult = client.exchange(fayunPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceFayunPage).getBody();
        if (fayunPageResult.getSuccess()) {
            logger.info("创建分页成功\nPOST {}\nrequest = {}\nresponse = {}", fayunPageUrl, "", printJson(fayunPageResult.getData()));
        } else {
            logger.info("创建分页失败: {}", fayunPageResult.getError());
            System.exit(-1);
        }

        // 3. id查询
        String fayunFindUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/fayuns/" + fayunCreateResult.getData().getId();
        Result<YingFayun> fayunFindResult = client.exchange(fayunFindUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceFayun).getBody();
        if (fayunFindResult.getSuccess()) {
            logger.info("查询发运成功\nPOST {}\nrequest = {}\nresponse = {}", fayunFindUrl, "", printJson(fayunFindResult.getData()));
        } else {
            logger.info("查询发运失败: {}", fayunFindResult.getError());
            System.exit(-1);
        }

        // 4. 更新
        fayun.setFyAmount(null);
        fayun.setOrderId(yingOrderResult.getData().getId());
        fayun.setId(fayunCreateResult.getData().getId());
        String fayunUpdateUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/fayuns/" + fayunCreateResult.getData().getId();

        Result<Integer> yingFayunUpdateResult = client.exchange(fayunUpdateUrl, HttpMethod.PUT, new HttpEntity<YingFayun>(fayun), typeReferenceInteger).getBody();
        if (yingFayunUpdateResult.getSuccess()) {
            logger.info("发运更新成功\nPUT {}\nrequest = {}\nresponse = {}", fayunUpdateUrl, printJson(fayun), printJson(yingFayunUpdateResult.getData()));
        } else {
            logger.error("发运更新失败: {}", yingFayunUpdateResult.getError());
            System.exit(-2);
        }
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
            setHuikuanMode(PayMode.ELEC_REMITTANCE);
            setHuikuanUsage(ReceivePaymentPurpose.PAYMENT_FOR_GOODS);
        }};
        Result<YingHuikuan> huikuanCreateResult = client.exchange(huikuanCreateUrl, HttpMethod.POST, new HttpEntity<>(huikuan), typeReferenceHuikuan).getBody();
        if (huikuanCreateResult.getSuccess()) {
            logger.info("创建回款成功\nPOST {}\nrequest = {}\nresponse = {}", huikuanCreateUrl, printJson(huikuan), printJson(huikuanCreateResult.getData()));
        } else {
            logger.info("创建回款失败: {}", huikuanCreateResult.getError());
            System.exit(-1);
        }


        // 回款 - 分页

        String huikuanPageUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/huikuans";
        PageYingHuikuanDTO pageYingHuikuanDTO = new PageYingHuikuanDTO();
        pageYingHuikuanDTO.setHsId(yingOrderConfigResult.getData().getId());
        PageResult<YingHuikuan> huikuanPageResult = client.exchange(huikuanPageUrl, HttpMethod.GET, new HttpEntity<PageYingHuikuanDTO>(pageYingHuikuanDTO), typeReferenceHuikuanPage).getBody();
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
            setSkCompanyId(17l);
            setHuankuankDate(stringToTime("2017-7-26"));
            setHuankuanAmount(new BigDecimal("511700.02"));
            setHuankuanFee(new BigDecimal("560"));
            setHuankuanInterest(new BigDecimal("1700.02"));

        }};
        Result<YingHuankuan> huankuanCreateResult = client.exchange(huankuanCreateUrl, HttpMethod.POST, new HttpEntity<>(huankuan), typeReferenceHuankuan).getBody();
        if (huankuanCreateResult.getSuccess()) {
            logger.info("创建还款成功\nPOST {}\nrequest = {}\nresponse = {}", huankuanCreateUrl, printJson(huankuan), printJson(huankuanCreateResult.getData()));
        } else {
            logger.info("创建还款失败: {}", huankuanCreateResult.getError());
            System.exit(-1);
        }

        // 2. 分页
        String huankuanPageUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/huankuans";
        PageYingHuankuanDTO pageYingHuankuanDTO = new PageYingHuankuanDTO();
        pageYingHuankuanDTO.setOrderId(yingOrderResult.getData().getId());
        pageYingHuankuanDTO.setHsId(yingOrderConfigResult.getData().getId());
        PageResult<YingHuankuan> huankuanPageResult = client.exchange(huankuanPageUrl, HttpMethod.GET, new HttpEntity<PageYingHuankuanDTO>(pageYingHuankuanDTO), typeReferenceHuankuanPage).getBody();
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

        // 4. 更新 todo
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
            setPayAmount(new BigDecimal("54291.93"));
            setCapitalId(17l);
        }};
        Result<YingFukuan> fukuanResult = client.exchange(fukuanCreateUrl, HttpMethod.POST, new HttpEntity<>(yingFukuan), typeReferenceFukuan).getBody();
        if (fukuanResult.getSuccess()) {
            logger.info("创建付款成功\nPOST {}\nrequest = {}\nresponse = {}", fukuanCreateUrl, printJson(yingFukuan), printJson(fukuanResult.getData()));
        } else {
            logger.info("创建付款失败: {}", fukuanResult.getError());
            System.exit(-1);
        }
        client.exchange(fukuanCreateUrl, HttpMethod.POST, new HttpEntity<>(yingFukuantwo), typeReferenceFukuan).getBody();

        // 2. 分页
        String fukuanPageUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/fukuans";
        Map<String, String> fukuanVariablesPage = new HashMap<>();
        fukuanVariablesPage.put("hsId", "22");
        PageYingFukuanDTO pageYingFukuanDTO = new PageYingFukuanDTO();
        pageYingFukuanDTO.setHsId(22l);
        pageYingFukuanDTO.setReceiveCompanyId(18l);
        PageResult<YingFukuan> fukuanPageResult = client.exchange(fukuanPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceFukuanPage, fukuanVariablesPage).getBody();
        if (fukuanPageResult.getSuccess()) {
            logger.info("付款分页成功\nPOST {}\nrequest = {}\nresponse = {}", fukuanPageUrl, "", printJson(fukuanPageResult.getData()));
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
                setHsId(yingOrderConfigResult.getData().getId());
                setSettleDate(stringToTime("2017-8-4"));
                setMoney(new BigDecimal("565994.59"));
                setDiscountType(DiscountMode.NO_DISCOUNT);
                setAmount(new BigDecimal("1510.61"));
            }
        };

        Result<YingSettleUpstream> upstreamCreateResult = client.exchange(upstreamCreateUrl, HttpMethod.POST, new HttpEntity<>(upstream), typeReferenceSettleUpstream).getBody();
        if (upstreamCreateResult.getSuccess()) {
            logger.info("创建上游结算成功\nPOST {}\nrequest = {}\nresponse = {}", upstreamCreateUrl, printJson(upstream), printJson(upstreamCreateResult.getData()));
        } else {
            logger.info("创建上游结算失败: {}", upstreamCreateResult.getError());
            System.exit(-1);
        }

        // 2. 分页
        String upstreamPageUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/settleupstream";
        logger.info("上游结算 {}", upstreamCreateUrl);
        PageYingSettleUpstreamDTO pageYingSettleUpstreamDTO = new PageYingSettleUpstreamDTO();
        pageYingSettleUpstreamDTO.setOrderId(yingOrderResult.getData().getId());
        PageResult<YingSettleUpstream> upstreamPageResult = client.exchange(upstreamPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceSettleUpstreamPage).getBody();
        if (upstreamPageResult.getSuccess()) {
            logger.info("创建分页成功\nPOST {}\nrequest = {}\nresponse = {}", upstreamPageUrl, "", printJson(upstreamPageResult.getData()));
        } else {
            logger.info("创建分页失败: {}", upstreamPageResult.getError());
            System.exit(-1);
        }

        // 3. id 查询
        String upstreamFindUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/settleupstream/" + upstreamCreateResult.getData().getId();
        Result<YingSettleUpstream> upstreamFindResult = client.exchange(upstreamFindUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceSettleUpstream).getBody();
        if (upstreamFindResult.getSuccess()) {
            logger.info("上游结算成功\nPOST {}\nrequest = {}\nresponse = {}", upstreamFindUrl, "", printJson(upstreamFindResult.getData()));
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
        Result<YingSettleDownstream> downstreamCreateResult = client.exchange(downstreamCreateUrl, HttpMethod.POST, new HttpEntity<>(downstream), typeReferenceSettleDownstream).getBody();
        if (downstreamCreateResult.getSuccess()) {
            logger.info("创建下游结算成功\nGET {}\nrequest = {}\nresponse = {}", downstreamCreateUrl, printJson(downstream), printJson(downstreamCreateResult.getData()));
        } else {
            logger.info("创建下游结算失败: {}", downstreamCreateResult.getError());
            System.exit(-1);
        }

        // 2. 分页
        String downstreamPageUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/settledownstream";
        PageYingSettleDownstreamDTO dto = new PageYingSettleDownstreamDTO();
        dto.setOrderId(yingOrderResult.getData().getId());

        PageResult<YingSettleDownstream> downstreamPageResult = client.exchange(downstreamPageUrl, HttpMethod.GET, new HttpEntity<PageYingSettleDownstreamDTO>(dto), typeReferenceSettleDownstreamPage).getBody();
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
        Result<YingSettleTraffic> trafficCreateResult = client.exchange(trafficCreateUrl, HttpMethod.POST, new HttpEntity<>(traffic), typeReferenceSettleTraffic).getBody();
        if (trafficCreateResult.getSuccess()) {
            logger.info("创建运输方结算成功\n Get {}\nrequest = {}\nresponse = {}", trafficCreateUrl, printJson(traffic), printJson(trafficCreateResult.getData()));
        } else {
            logger.info("创建运输方结算失败: {}", trafficCreateResult.getError());
            System.exit(-1);
        }

        // 2. 分页
        String trafficPageUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/settletraffic";
        Map<String, Object> trafficVariablesPage = new HashMap<>();
        trafficVariablesPage.put("orderId", "" + yingOrderResult.getData().getId());
        PageYingSettleTrafficDTO trafficDTO = new PageYingSettleTrafficDTO();
        trafficDTO.setOrderId(yingOrderResult.getData().getId());
        PageResult<YingSettleTraffic> trafficPageResult = client.exchange(trafficPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceSettleTrafficPage, trafficVariablesPage).getBody();
        if (trafficPageResult.getSuccess()) {
            logger.info("运输方结算分页成功\nPOST {}\nrequest = {}\nresponse = {}", trafficPageUrl, "", printJson(trafficPageResult.getData()));
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
        String feePageUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/fees";
        PageResult<YingFee> feePageResult = client.exchange(feePageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceFeePage).getBody();
        if (feePageResult.getSuccess()) {
            logger.info("费用分页查询成功\nGET {}\nrequest = {}\nresponse = {}", feePageUrl, "", printJson(feePageResult.getData()));
        } else {
            logger.info("费用分页查询失败: {}", feePageResult.getError());
            System.exit(-1);
        }

        // 3. 查询
        String feeFindUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/fees";
        Result<YingFee> feeFindResult = client.exchange(feeFindUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceFee).getBody();
        if (feeFindResult.getSuccess()) {
            logger.info("费用查询成功\nGET {}\nrequest = {}\nresponse = {}", feeFindUrl, "", printJson(feeFindResult.getData()));
        } else {
            logger.info("费用查询失败: {}", feeFindResult.getError());
            System.exit(-1);
        }

        // 4. 更新
        String fayunUpdateUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/fees/" + yingOrderResult.getData().getId();
        fee.setAmount(new BigDecimal("9999"));
        fee.setOrderId(yingOrderResult.getData().getId());
        fee.setId(feeCreateResult.getData().getId());
        Result<Integer> yingFayunUpdateResult = client.exchange(fayunUpdateUrl, HttpMethod.PUT, new HttpEntity<YingFee>(fee), typeReferenceInteger).getBody();
        if (yingFayunUpdateResult.getSuccess()) {
            logger.info("更新费用成功\nPOST {}\nrequest = {}\nresponse = {}", fayunUpdateUrl, printJson(fee), printJson(yingFayunUpdateResult.getData()));
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
        YingInvoice yingInvoiceSecond= new YingInvoice(
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
        Result<YingInvoice> invoiceCreateResult = client.exchange(invoiceCreateUrl, HttpMethod.POST, new HttpEntity<YingInvoice>(yingInvoice), typeReferenceInvoice).getBody();
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
        String invoicePageUrl = "/api/ying/" + yingOrderResult.getData().getId() + "/invoices";

        PageYingInvoiceDTO pageYingInvoiceDTO = new PageYingInvoiceDTO();
        pageYingInvoiceDTO.setOrderId(yingOrderResult.getData().getId());
        pageYingInvoiceDTO.setInvoiceType(InvoiceType.FRIGHT_INVOICE);
        Map<String, Object> invoiceVariablesPage = new HashMap<>();
//        invoiceVariablesPage.put("orderId", "" + yingOrderResult.getData().getId());
        invoiceVariablesPage.put("invoiceType", InvoiceType.FRIGHT_INVOICE);
        PageResult<YingInvoice> invoicePageResult = client.exchange(invoicePageUrl, HttpMethod.GET, new HttpEntity<PageYingInvoiceDTO>(pageYingInvoiceDTO), typeReferenceInvoicePage, invoiceVariablesPage).getBody();
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
            logger.info("更新发票成功\nPUT {}\nrequest = {}\nresponse = {}", invoiceUpdateUrl, "", printJson(invoiceUpdateResult.getData()));
        } else {
            logger.info("更新发票失败: {}", invoiceUpdateResult.getError());
            System.exit(-1);
        }
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