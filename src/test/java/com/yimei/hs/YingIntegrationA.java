package com.yimei.hs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.hs.boot.api.Logutil;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.cang.dto.PageCangChukuDTO;
import com.yimei.hs.cang.dto.PageCangRukuDTO;
import com.yimei.hs.cang.entity.CangChuku;
import com.yimei.hs.cang.entity.CangRuku;
import com.yimei.hs.enums.*;
import com.yimei.hs.same.dto.*;
import com.yimei.hs.same.entity.*;
import com.yimei.hs.test.HsTestBase;
import com.yimei.hs.user.entity.Dept;
import com.yimei.hs.user.entity.Party;
import com.yimei.hs.user.entity.Team;
import com.yimei.hs.user.entity.User;
import com.yimei.hs.util.WebUtils;
import com.yimei.hs.ying.dto.*;
import com.yimei.hs.ying.entity.YingFayun;
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

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by hary on 2017/9/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = HsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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


    Result<Order> yingOrderResult = null;
    Result<OrderConfig> yingOrderConfigResult = null;


    Result<Invoice> invoiceCreateResult = null;
    Result<Fee> feeFindResult = null;
    Result<SettleTraffic> trafficCreateResult = null;

    Result<SettleBuyer> settleBuyerCreateResult = null;
    Result<SettleSeller> settleSellerCreateResult = null;

    Result<Fukuan> fukuanResult = null;
    Result<Huankuan> huankuanCreateResult = null;
    Result<Huikuan> huikuanCreateResult = null;

    Result<Jiekuan> jiekuanCreateResult = null;

    Result<YingFayun> fayunCreateResult = null;

    @Test
    public void yingIntegration() throws Exception {
        System.out.println("开始应收集成测试");
        LocalDateTime  startTime = LocalDateTime.now();
        LocalDateTime  endTime = LocalDateTime.of(2017, 11, 22, 21, 00);
//        Duration duration = java.time.Duration.between(startTime,  endTime );
//        Duration duration2 = java.time.Duration.between(endTime,  startTime );

        System.out.println("day：" + endTime.compareTo(startTime)+"  isbefore "+endTime.isBefore(startTime));
        System.out.println("day：" + startTime.compareTo(endTime)+"  isbefore "+startTime.isBefore(endTime));
//        java.time.Duration.
//        defaultUser();
//        order();
//        config();
//        fee();
//        traffic();
//        ruku();
//        chuku();
//        huikuan();
//        fukuan();
//        fayun();
//        jiekuan();
//        huankuan();
//        huankuanAuto();
//        jiekuan_huankuan();
//
//        buyer();
//        seller();

//        buyerCang();
//        sellerCang();
    }

    public void order() throws JsonProcessingException {
        // 7. 创建应收订单
        Order yingOrder = new Order();
        yingOrder.setBusinessType(BusinessType.ying);
        yingOrder.setTeamId(1L); // 赵善文团队
        yingOrder.setCargoType(CargoType.COAL);
        yingOrder.setUpstreamId(partyIds.get(0));   // 上游
        yingOrder.setDownstreamId(partyIds.get(1));  // 下游
        yingOrder.setMainAccounting(2L);
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

    private void config() throws JsonProcessingException {

        // 1. 增加核算月配置
        String configCreateUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/units";

        OrderConfig config = new OrderConfig() {{
            setHsMonth("201712");
            setContractBaseInterest(new BigDecimal("0.20"));
            setMaxPrepayRate(new BigDecimal("0.90"));
            setUnInvoicedRate(new BigDecimal("0.7"));
            setExpectHKDays(45);
            setTradeAddPrice(new BigDecimal("0"));
            setWeightedPrice(new BigDecimal("612"));
        }};
        Result<OrderConfig> yingOrderConfigResult = client.exchange(configCreateUrl, HttpMethod.POST, new HttpEntity<OrderConfig>(config), typeReferenceOrderConfig).getBody();
        if (yingOrderConfigResult.getSuccess()) {
            logger.info("添加核算月配置成功\nPOST {}\nrequest:{}\nresponse:{}", configCreateUrl, printJson(config), printJson(yingOrderConfigResult.getData()));
        } else {
            logger.error("添加核算月配置失败: {}", yingOrderConfigResult.getError());
            System.exit(-2);
        }

        // 2. 修改核算月配置
        OrderConfig yingOrderConfig = new OrderConfig() {{
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
        String configUpdateUrl = "/api/business/ying/" + yingOrderConfigResult.getData().getOrderId() + "/units/" + yingOrderConfigResult.getData().getId();
        Result<Integer> yingOrderConfigUpdateResult = client.exchange(configUpdateUrl, HttpMethod.PUT, new HttpEntity<OrderConfig>(yingOrderConfig), typeReferenceInteger).getBody();
        if (yingOrderConfigUpdateResult.getSuccess()) {
            logger.info("更新核算月配置成功\nPUT {}\nrequest = {}\nresponse = {}", configUpdateUrl, printJson(yingOrderConfig), printJson(yingOrderConfigUpdateResult.getData()));
        } else {
            logger.error("更新核算月配置失败: {}", yingOrderConfigUpdateResult.getError());
            System.exit(-2);
        }

        // 3. 核算月分页查询  query by orderId

        Map<String, Object> variablesHs = WebUtils.getUrlVariables(PageOrderConfigDTO.class);
        variablesHs.put("orderId", "" + yingOrderResult.getData().getId());
        variablesHs.put("pageSize", 5);
        variablesHs.put("pageNo", 1);
        String configPageUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/units?" + WebUtils.getUrlTemplate(PageOrderConfigDTO.class);
        Result<Page<OrderConfig>> yingOrderConfigPageResult = client.exchange(configPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceOrderConfigPage, variablesHs).getBody();
        if (yingOrderConfigPageResult.getSuccess()) {
            logger.info("获取核算月配置分页成功\nGET /api/ying \nrequest: {}\nresponse:\n{}", printJson(variablesHs), printJson(yingOrderConfigPageResult.getData()));
        } else {
            logger.error("获取核算月配置分页失败: {}", yingOrderConfigPageResult.getError());
            System.exit(-1);
        }

        this.yingOrderConfigResult = new Result<OrderConfig>(true, yingOrderConfigPageResult.getData().getResults().get(0), null);

    }

    private void fee() throws JsonProcessingException {
        // 1. 添加费用
        String feeCreateUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/fees";
        Fee fee = new Fee() {{
            setHsId(yingOrderConfigResult.getData().getId());
            setOrderId(yingOrderResult.getData().getId());
            setAmount(new BigDecimal("100"));
            setName(FeeClass.SERVICE_FEE);
        }};
        Result<Fee> feeCreateResult = client.exchange(feeCreateUrl, HttpMethod.POST, new HttpEntity<>(fee), typeReferenceFee).getBody();
        if (feeCreateResult.getSuccess()) {
            logger.info("创建费用成功\nPOST {}\nrequest = {}\nresponse = {}", feeCreateUrl, printJson(fee), printJson(feeCreateResult.getData()));
        } else {
            logger.info("创建费用失败: {}", feeCreateResult.getError());
            System.exit(-1);
        }


        // 2. 分页
        String feePageUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/fees?" + WebUtils.getUrlTemplate(PageFeeDTO.class);

        Map<String, Object> feeVariables = WebUtils.getUrlVariables(PageYingFeeDTO.class);
        feeVariables.put("orderId", yingOrderResult.getData().getId());
        feeVariables.put("pageSize", 5);
        feeVariables.put("pageNo", 1);

        Result<Page<Fee>> feePageResult = client.exchange(feePageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceFeePage, feeVariables).getBody();
        if (feePageResult.getSuccess()) {
            logger.info("费用分页查询成功\nGET {}\nrequest = {}\nresponse = {}", feePageUrl, "", printJson(feePageResult.getData()));
        } else {
            logger.info("费用分页查询失败: {}", feePageResult.getError());
            System.exit(-1);
        }

        // 3. 查询
        String feeFindUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/fees/" + feeCreateResult.getData().getId();
        feeFindResult = client.exchange(feeFindUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceFee).getBody();
        if (feeFindResult.getSuccess()) {
            logger.info("费用查询成功\nGET {}\nrequest = {}\nresponse = {}", feeFindUrl, "", printJson(feeFindResult.getData()));
        } else {
            logger.info("费用查询失败: {}", feeFindResult.getError());
            System.exit(-1);
        }

        // 4. 更新
        String fayunUpdateUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/fees/" + feeFindResult.getData().getId();
        fee.setAmount(new BigDecimal("9999"));
        fee.setOrderId(yingOrderResult.getData().getId());
        fee.setId(feeCreateResult.getData().getId());
        Result<Integer> yingFayunUpdateResult = client.exchange(fayunUpdateUrl, HttpMethod.PUT, new HttpEntity<Fee>(fee), typeReferenceInteger).getBody();
        if (yingFayunUpdateResult.getSuccess()) {
            logger.info("更新费用成功\nPUT {}\nrequest = {}\nresponse = {}", fayunUpdateUrl, printJson(fee), printJson(yingFayunUpdateResult.getData()));
        } else {
            logger.error("更新费用失败: {}", yingFayunUpdateResult.getError());
            System.exit(-2);
        }
    }

    private void buyer() throws JsonProcessingException {
        // 1. 添加下游结算
        String buyerCreateUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/settlebuyerdownstream";
        SettleBuyer downstream = new SettleBuyer(
        ) {{
            setAmount(new BigDecimal("1510.61"));
            setMoney(new BigDecimal("55968.26"));
            setHsId(yingOrderConfigResult.getData().getId());
            setOrderId(yingOrderResult.getData().getId());
            setSettleDate(stringToTime("2017-07-07"));

            setSettleGap(new BigDecimal("0"));
        }};
        settleBuyerCreateResult = client.exchange(buyerCreateUrl, HttpMethod.POST, new HttpEntity<>(downstream), typeReferenceSettleBuyer).getBody();
        if (settleBuyerCreateResult.getSuccess()) {
            logger.info("创建应收下游结算成功\nPOST {}\nrequest = {}\nresponse = {}", buyerCreateUrl, printJson(downstream), printJson(settleBuyerCreateResult.getData()));
        } else {
            logger.info("创建应收下游结算失败: {}", settleBuyerCreateResult.getError());
            System.exit(-1);
        }

        // 2. 分页
        String buyerPageUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/settlebuyerdownstream?" + WebUtils.getUrlTemplate(PageSettleBuyerDTO.class);
        Map<String, Object> buyerVariables = WebUtils.getUrlVariables(PageSettleBuyerDTO.class);
        buyerVariables.put("orderId", yingOrderResult.getData().getId());
        buyerVariables.put("pageSize", 5);
        buyerVariables.put("pageNo", 1);


        Result<Page<SettleBuyer>> buyerPageResult = client.exchange(buyerPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceSettleBuyerPage, buyerVariables).getBody();
        if (buyerPageResult.getSuccess()) {
            logger.info("应收下游结算分页成功\nGET {}\nrequest = {}\nresponse = {}", buyerPageUrl, "", printJson(buyerPageResult.getData()));
        } else {
            logger.info("应收下游结算分页失败: {}", buyerPageResult.getError());
            System.exit(-1);
        }

        // 3. id查询

        String buyerFindUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/settlebuyerdownstream/" + settleBuyerCreateResult.getData().getId();
        Result<SettleBuyer> buyerFindResult = client.exchange(buyerFindUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceSettleBuyer).getBody();
        if (buyerFindResult.getSuccess()) {
            logger.info("应收下游结算查询成功\n Get {}\nrequest = {}\nresponse = {}", buyerFindUrl, "", printJson(buyerFindResult.getData()));
        } else {
            logger.info("应收下游结算查询失败: {}", buyerFindResult.getError());
            System.exit(-1);
        }




        // 4. 更新
        String buyerUpdateUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/settlebuyerdownstream/" + settleBuyerCreateResult.getData().getId();
        downstream.setAmount(new BigDecimal("9999"));
        downstream.setId(settleBuyerCreateResult.getData().getId());
        Result<Integer> upstreamUpdateResult = client.exchange(buyerUpdateUrl, HttpMethod.PUT, new HttpEntity<SettleBuyer>(downstream), typeReferenceInteger).getBody();
        if (upstreamUpdateResult.getSuccess()) {
            logger.info("更新应收下游游结算成功\nPOST {}\nrequest = {}\nresponse = {}", buyerUpdateUrl, printJson(downstream), printJson(upstreamUpdateResult.getData()));
        } else {
            logger.error("更新应收下游游结算失败: {}", upstreamUpdateResult.getError());
            System.exit(-2);
        }
    }

    private void buyerCang() throws JsonProcessingException {
        // 1. 添加下游结算
        String buyerCreateUrl = "/api/business/cang/" + yingOrderResult.getData().getId() + "/settlebuyerupstream";
        SettleBuyer downstream = new SettleBuyer(
        ) {{
            setAmount(new BigDecimal("1510.61"));
            setMoney(new BigDecimal("55968.26"));
            setHsId(yingOrderConfigResult.getData().getId());
            setOrderId(yingOrderResult.getData().getId());
            setSettleDate(stringToTime("2017-07-07"));

            setSettleGap(new BigDecimal("0"));
        }};
        settleBuyerCreateResult = client.exchange(buyerCreateUrl, HttpMethod.POST, new HttpEntity<>(downstream), typeReferenceSettleBuyer).getBody();
        if (settleBuyerCreateResult.getSuccess()) {
            logger.info("创建仓押上游结算成功\nPOST {}\nrequest = {}\nresponse = {}", buyerCreateUrl, printJson(downstream), printJson(settleBuyerCreateResult.getData()));
        } else {
            logger.info("创建仓押上游结算失败: {}", settleBuyerCreateResult.getError());
            System.exit(-1);
        }

        // 2. 分页
        String buyerPageUrl = "/api/business/cang/" + yingOrderResult.getData().getId() + "/settlebuyerupstream?" + WebUtils.getUrlTemplate(PageSettleBuyerDTO.class);
        Map<String, Object> buyerVariables = WebUtils.getUrlVariables(PageSettleBuyerDTO.class);
        buyerVariables.put("orderId", yingOrderResult.getData().getId());
        buyerVariables.put("pageSize", 5);
        buyerVariables.put("pageNo", 1);


        Result<Page<SettleBuyer>> buyerPageResult = client.exchange(buyerPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceSettleBuyerPage, buyerVariables).getBody();
        if (buyerPageResult.getSuccess()) {
            logger.info("仓押上游结算分页成功\nGET {}\nrequest = {}\nresponse = {}", buyerPageUrl, "", printJson(buyerPageResult.getData()));
        } else {
            logger.info("仓押上游结算分页失败: {}", buyerPageResult.getError());
            System.exit(-1);
        }

        // 3. id查询

        String buyerFindUrl = "/api/business/cang/" + yingOrderResult.getData().getId() + "/settlebuyerupstream/" + settleBuyerCreateResult.getData().getId();
        Result<SettleBuyer> buyerFindResult = client.exchange(buyerFindUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceSettleBuyer).getBody();
        if (buyerFindResult.getSuccess()) {
            logger.info("仓押上游结算查询成功\n Get {}\nrequest = {}\nresponse = {}", buyerFindUrl, "", printJson(buyerFindResult.getData()));
        } else {
            logger.info("仓押上游结算查询失败: {}", buyerFindResult.getError());
            System.exit(-1);
        }




        // 4. 更新
        String buyerUpdateUrl = "/api/business/cang/" + yingOrderResult.getData().getId() + "/settlebuyerupstream/" + settleBuyerCreateResult.getData().getId();
        downstream.setAmount(new BigDecimal("9999"));
        downstream.setId(settleBuyerCreateResult.getData().getId());
        Result<Integer> upstreamUpdateResult = client.exchange(buyerUpdateUrl, HttpMethod.PUT, new HttpEntity<SettleBuyer>(downstream), typeReferenceInteger).getBody();
        if (upstreamUpdateResult.getSuccess()) {
            logger.info("更新仓押上游游结算成功\nPOST {}\nrequest = {}\nresponse = {}", buyerUpdateUrl, printJson(downstream), printJson(upstreamUpdateResult.getData()));
        } else {
            logger.error("更新仓押上游游结算失败: {}", upstreamUpdateResult.getError());
            System.exit(-2);
        }
    }
    private void seller() throws JsonProcessingException {
        // 1. 添加上游结算

        String upstreamCreateUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/settlesellerupstream";
        SettleSeller upstream = new SettleSeller() {
            {
                setOrderId(yingOrderResult.getData().getId());
                setHsId(yingOrderConfigResult.getData().getId());
                setSettleDate(stringToTime("2017-8-4"));
                setMoney(new BigDecimal("565994.59"));
                setDiscountType(DiscountMode.NO_DISCOUNT);
                setAmount(new BigDecimal("1510.61"));
            }
        };

        settleSellerCreateResult = client.exchange(upstreamCreateUrl, HttpMethod.POST, new HttpEntity<>(upstream), typeReferenceSettleSeller).getBody();
        if (settleSellerCreateResult.getSuccess()) {
            logger.info("创建应收上游结算成功\nPOST {}\nrequest = {}\nresponse = {}", upstreamCreateUrl, printJson(upstream), printJson(settleSellerCreateResult.getData()));
        } else {
            logger.info("创建应收上游结算失败: {}", settleSellerCreateResult.getError());
            System.exit(-1);
        }

        // 2. 分页
        String upstreamPageUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/settlesellerupstream?" + WebUtils.getUrlTemplate(PageSettleSellerDTO.class);

        Map<String, Object> upstreamVariables = WebUtils.getUrlVariables(PageSettleSellerDTO.class);
        upstreamVariables.put("orderId", yingOrderResult.getData().getId());
        upstreamVariables.put("pageSize", 5);
        upstreamVariables.put("pageNo", 1);

        Result<Page<SettleSeller>> upstreamPageResult = client.exchange(upstreamPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceSettleSellerPage, upstreamVariables).getBody();
        if (upstreamPageResult.getSuccess()) {
            logger.info("应收上游结算分页成功\nGET {}\nrequest = {}\nresponse = {}", upstreamPageUrl, "", printJson(upstreamPageResult.getData()));
        } else {
            logger.info("应收上游结算分页失败: {}", upstreamPageResult.getError());
            System.exit(-1);
        }

        // 3. id 查询
        String upstreamFindUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/settlesellerupstream/" + settleSellerCreateResult.getData().getId();
        Result<SettleSeller> upstreamFindResult = client.exchange(upstreamFindUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceSettleSeller).getBody();
        if (upstreamFindResult.getSuccess()) {
            logger.info("应收上游结算成功\nGET {}\nrequest = {}\nresponse = {}", upstreamFindUrl, "", printJson(upstreamFindResult.getData()));
        } else {
            logger.info("应收上游结算失败: {}", upstreamFindResult.getError());
            System.exit(-1);
        }

        // 4. 更新
        String fayunUpdateUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/settlesellerupstream/" + settleSellerCreateResult.getData().getId();
        upstream.setAmount(new BigDecimal("9999"));
        upstream.setId(settleSellerCreateResult.getData().getId());
        Result<Integer> upstreamUpdateResult = client.exchange(fayunUpdateUrl, HttpMethod.PUT, new HttpEntity<SettleSeller>(upstream), typeReferenceInteger).getBody();
        if (upstreamUpdateResult.getSuccess()) {
            logger.info("更新应收上游结算成功\nPOST {}\nrequest = {}\nresponse = {}", fayunUpdateUrl, printJson(upstream), printJson(upstreamUpdateResult.getData()));
        } else {
            logger.error("更新应收上游结算失败: {}", upstreamUpdateResult.getError());
            System.exit(-2);
        }
    }

    private void sellerCang() throws JsonProcessingException {
        // 1. 添加上游结算

        String upstreamCreateUrl = "/api/business/cang/" + yingOrderResult.getData().getId() + "/settlesellerdownstream";
        SettleSeller upstream = new SettleSeller() {
            {
                setOrderId(yingOrderResult.getData().getId());
                setHsId(yingOrderConfigResult.getData().getId());
                setSettleDate(stringToTime("2017-8-4"));
                setMoney(new BigDecimal("565994.59"));
                setDiscountType(DiscountMode.NO_DISCOUNT);
                setAmount(new BigDecimal("1510.61"));
            }
        };

        settleSellerCreateResult = client.exchange(upstreamCreateUrl, HttpMethod.POST, new HttpEntity<>(upstream), typeReferenceSettleSeller).getBody();
        if (settleSellerCreateResult.getSuccess()) {
            logger.info("创建仓押下游结算成功\nPOST {}\nrequest = {}\nresponse = {}", upstreamCreateUrl, printJson(upstream), printJson(settleSellerCreateResult.getData()));
        } else {
            logger.info("创建仓押下游结算失败: {}", settleSellerCreateResult.getError());
            System.exit(-1);
        }

        // 2. 分页
        String upstreamPageUrl = "/api/business/cang/" + yingOrderResult.getData().getId() + "/settlesellerdownstream?" + WebUtils.getUrlTemplate(PageSettleSellerDTO.class);

        Map<String, Object> upstreamVariables = WebUtils.getUrlVariables(PageSettleSellerDTO.class);
        upstreamVariables.put("orderId", yingOrderResult.getData().getId());
        upstreamVariables.put("pageSize", 5);
        upstreamVariables.put("pageNo", 1);

        Result<Page<SettleSeller>> upstreamPageResult = client.exchange(upstreamPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceSettleSellerPage, upstreamVariables).getBody();
        if (upstreamPageResult.getSuccess()) {
            logger.info("仓押下游结算创建分页成功\nGET {}\nrequest = {}\nresponse = {}", upstreamPageUrl, "", printJson(upstreamPageResult.getData()));
        } else {
            logger.info("仓押下游结算创建分页失败: {}", upstreamPageResult.getError());
            System.exit(-1);
        }

        // 3. id 查询
        String upstreamFindUrl = "/api/business/cang/" + yingOrderResult.getData().getId() + "/settlesellerdownstream/" + settleSellerCreateResult.getData().getId();
        Result<SettleSeller> upstreamFindResult = client.exchange(upstreamFindUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceSettleSeller).getBody();
        if (upstreamFindResult.getSuccess()) {
            logger.info("仓押下游结算成功\nGET {}\nrequest = {}\nresponse = {}", upstreamFindUrl, "", printJson(upstreamFindResult.getData()));
        } else {
            logger.info("仓押下游结算失败: {}", upstreamFindResult.getError());
            System.exit(-1);
        }

        // 4. 更新
        String fayunUpdateUrl = "/api/business/cang" +
                "/" + yingOrderResult.getData().getId() + "/settlesellerdownstream/" + settleSellerCreateResult.getData().getId();
        upstream.setAmount(new BigDecimal("9999"));
        upstream.setId(settleSellerCreateResult.getData().getId());
        Result<Integer> upstreamUpdateResult = client.exchange(fayunUpdateUrl, HttpMethod.PUT, new HttpEntity<SettleSeller>(upstream), typeReferenceInteger).getBody();
        if (upstreamUpdateResult.getSuccess()) {
            logger.info("更新仓押下游结算成功\nPOST {}\nrequest = {}\nresponse = {}", fayunUpdateUrl, printJson(upstream), printJson(upstreamUpdateResult.getData()));
        } else {
            logger.error("更新仓押下游结算失败: {}", upstreamUpdateResult.getError());
            System.exit(-2);
        }
    }
    private void traffic() throws JsonProcessingException {

        // 1. 添加运输方结算
        String trafficCreateUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/settletraffic";
        SettleTraffic traffic = new SettleTraffic() {{
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
        String trafficPageUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/settletraffic?" + WebUtils.getUrlTemplate(PageSettleTrafficDTO.class);

        Map<String, Object> trafficVariables = WebUtils.getUrlVariables(PageSettleTrafficDTO.class);
        trafficVariables.put("orderId", yingOrderResult.getData().getId());
        trafficVariables.put("pageSize", 5);
        trafficVariables.put("pageNo", 1);


        PageSettleTrafficDTO trafficDTO = new PageSettleTrafficDTO();
        trafficDTO.setOrderId(yingOrderResult.getData().getId());
        Result<Page<SettleTraffic>> trafficPageResult = client.exchange(trafficPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceSettleTrafficPage, trafficVariables).getBody();
        if (trafficPageResult.getSuccess()) {
            logger.info("运输方结算分页成功\nGET {}\nrequest = {}\nresponse = {}", trafficPageUrl, "", printJson(trafficPageResult.getData()));
        } else {
            logger.info("运输方结算分页失败: {}", trafficPageResult.getError());
            System.exit(-1);
        }

        // 3. id查询
        String trafficFindUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/settletraffic/" + trafficCreateResult.getData().getId();
        Result<SettleTraffic> trafficFindResult = client.exchange(trafficFindUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceSettleTraffic).getBody();
        if (trafficFindResult.getSuccess()) {
            logger.info("查询运输方结算成功\nGET {}\nrequest = {}\nresponse = {}", trafficFindUrl, "", printJson(trafficFindResult.getData()));
        } else {
            logger.info("查询运输方结算失败: {}", trafficFindResult.getError());
            System.exit(-1);
        }

        // 4. 更新
        String fayunUpdateUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/settletraffic/" + trafficCreateResult.getData().getId();
        traffic.setAmount(new BigDecimal("9999"));
        traffic.setOrderId(yingOrderResult.getData().getId());
        traffic.setId(trafficCreateResult.getData().getId());
        Result<Integer> yingFayunUpdateResult = client.exchange(fayunUpdateUrl, HttpMethod.PUT, new HttpEntity<SettleTraffic>(traffic), typeReferenceInteger).getBody();
        if (yingFayunUpdateResult.getSuccess()) {
            logger.info("更新输方结算成功\nPOST {}\nrequest = {}\nresponse = {}", fayunUpdateUrl, printJson(traffic), printJson(yingFayunUpdateResult.getData()));
        } else {
            logger.error("更新输方结算失败: {}", yingFayunUpdateResult.getError());
            System.exit(-2);
        }
    }

    private void huikuan() throws JsonProcessingException {

        // 1. 添加回款
        String huikuanCreateUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/huikuans";
        Huikuan huikuan = new Huikuan() {{
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

        String huikuanPageUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/huikuans?" + WebUtils.getUrlTemplate(PageHuikuanDTO.class);

        Map<String, Object> variablesHuikuan = WebUtils.getUrlVariables(PageHuikuanDTO.class);
        variablesHuikuan.put("orderId", yingOrderResult.getData().getId());
        variablesHuikuan.put("pageSize", 5);
        variablesHuikuan.put("pageNo", 1);


        Result<Page<Huikuan>> huikuanPageResult = client.exchange(huikuanPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceHuikuanPage, variablesHuikuan).getBody();

        if (huikuanPageResult.getSuccess()) {
            logger.info("创建分页成功\n GET {}\nrequest = {}\nresponse = {}", huikuanPageUrl, "", printJson(huikuanPageResult.getData()));
        } else {
            logger.info("创建分页失败: {}", huikuanPageResult.getError());
            System.exit(-1);
        }

        // 3. id查询
        String huikuanFindUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/huikuans/" + huikuanCreateResult.getData().getId();
        Result<Huikuan> huikuanFindResult = client.exchange(huikuanFindUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceHuikuan).getBody();
        if (huikuanFindResult.getSuccess()) {
            logger.info("查询回款成功\nGET {}\nrequest = {}\nresponse = {}", huikuanFindUrl, "", printJson(huikuanFindResult.getData()));
        } else {
            logger.info("查询回款失败: {}", huikuanFindResult.getError());
            System.exit(-1);
        }

    }

    private void jiekuan() throws JsonProcessingException {

        // 1. 添加借款
        String jiekuanCreateUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/jiekuans";
        Jiekuan jiekuan = new Jiekuan() {{
            setOrderId(yingOrderResult.getData().getId());
            setHsId(yingOrderConfigResult.getData().getId());
            setFukuanId(0L);
            setJiekuanDate(stringToTime("20170706"));
            setCapitalId(1L);
            setAmount(new BigDecimal("510000.00"));
            setUseDays(35);
            setUseInterest(new BigDecimal("7.50"));

        }};
        jiekuanCreateResult = client.exchange(jiekuanCreateUrl, HttpMethod.POST, new HttpEntity<>(jiekuan), typeReferenceJiekuan).getBody();
        if (jiekuanCreateResult.getSuccess()) {
            logger.info("创建借款成功\nPOST {}\nrequest = {}\nresponse = {}", jiekuanCreateUrl, printJson(jiekuan), printJson(jiekuanCreateResult.getData()));
        } else {
            logger.info("创建借款失败: {}", jiekuanCreateResult.getError());
            System.exit(-1);
        }


        // 借款 - 分页

        String jiekuanPageUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/jiekuans?" + WebUtils.getUrlTemplate(PageJiekuanDTO.class);

        Map<String, Object> variablesHuikuan = WebUtils.getUrlVariables(PageJiekuanDTO.class);
        variablesHuikuan.put("orderId", yingOrderResult.getData().getId());
        variablesHuikuan.put("pageSize", 5);
        variablesHuikuan.put("pageNo", 1);


        Result<Page<Jiekuan>> jiekuanPageResult = client.exchange(jiekuanPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceJiekuanPage, variablesHuikuan).getBody();

        if (jiekuanPageResult.getSuccess()) {
            logger.info("创建分页成功\n GET {}\nrequest = {}\nresponse = {}", jiekuanPageUrl, "", printJson(jiekuanPageResult.getData()));
        } else {
            logger.info("创建分页失败: {}", jiekuanPageResult.getError());
            System.exit(-1);
        }

        // 3. id查询
        String jiekuanFindUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/jiekuans/" + jiekuanCreateResult.getData().getId();
        Result<Jiekuan> jiekuanFindResult = client.exchange(jiekuanFindUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceJiekuan).getBody();
        if (jiekuanFindResult.getSuccess()) {
            logger.info("查询借款成功\nPUT {}\nrequest = {}\nresponse = {}", jiekuanFindUrl, "", printJson(jiekuanFindResult.getData()));
        } else {
            logger.info("查询借款失败: {}", jiekuanFindResult.getError());
            System.exit(-1);
        }

        jiekuan=jiekuanFindResult.getData();
        jiekuan.setAmount(new BigDecimal("6666666"));
        // 4.更新
        String jiekuanUpdateUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/jiekuans/" + jiekuanCreateResult.getData().getId();
        Result<Integer> jiekuanUpdateResult = client.exchange(jiekuanUpdateUrl, HttpMethod.PUT, new HttpEntity<>(jiekuan), typeReferenceInteger).getBody();
        if (jiekuanUpdateResult.getSuccess()) {
            logger.info("更新借款成功\nGET {}\nrequest = {}\nresponse = {}", jiekuanUpdateUrl, "", printJson(jiekuanUpdateResult.getData()));
        } else {
            logger.info("更新借款失败: {}", jiekuanUpdateResult.getError());
            System.exit(-1);
        }

    }

    private void huankuan() throws JsonProcessingException {
        // 1. 添加还款
        String huankuanCreateUrl = "/api/business/cang/" + yingOrderResult.getData().getId() + "/huankuans";
        Huankuan huankuan = new Huankuan() {{


            List<HuankuanMap> hukuanMapList = new ArrayList<>();
            HuankuanMap map = new HuankuanMap();
            // map.setOrderId(1L);
            map.setPrincipal(new BigDecimal("510000"));
            map.setInterest(new BigDecimal("1700.02"));
            map.setFee(new BigDecimal("570.0"));
            map.setJiekuanId(jiekuanCreateResult.getData()
            .getId());

            hukuanMapList.add(map);
            setOrderId(yingOrderResult.getData().getId());
            setHsId(yingOrderConfigResult.getData().getId());
            setHuankuanDate(stringToTime("2017-7-26"));
            setHuankuanMapList(hukuanMapList);

        }};
        huankuanCreateResult = client.exchange(huankuanCreateUrl, HttpMethod.POST, new HttpEntity<>(huankuan), typeReferenceHuankuan).getBody();
        if (huankuanCreateResult.getSuccess()) {
            logger.info("创建还款成功\nPOST {}\nrequest = {}\nresponse = {}", huankuanCreateUrl, printJson(huankuan), printJson(huankuanCreateResult.getData()));
        } else {
            logger.info("创建还款失败: {}", huankuanCreateResult.getError());
            System.exit(-1);
        }

        // 2. 分页
        String huankuanPageUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/huankuans?" + WebUtils.getUrlTemplate(PageHuankuanDTO.class);
        Map<String, Object> variableshuankuan = WebUtils.getUrlVariables(PageHuankuanDTO.class);
        variableshuankuan.put("orderId", yingOrderResult.getData().getId());
        variableshuankuan.put("pageSize", 5);
        variableshuankuan.put("pageNo", 1);

        Result<Page<Huankuan>> huankuanPageResult = client.exchange(huankuanPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceHuankuanPage, variableshuankuan).getBody();
        if (huankuanPageResult.getSuccess()) {
            logger.info("还款  分页成功\n GET {}\nrequest = {}\nresponse = {}", huankuanPageUrl, "", printJson(huankuanPageResult.getData()));
        } else {
            logger.info("还款  分页失败: {}", huankuanPageResult.getError());
            System.exit(-1);
        }

        // 3. id查询
        String huankuanFindUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/huankuans/" + huankuanCreateResult.getData().getId();
        Result<Huankuan> huankuanFindResult = client.exchange(huankuanFindUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceHuankuan).getBody();
        if (huankuanFindResult.getSuccess()) {
            logger.info("查询  还款成功\n  GET {}\nrequest = {}\nresponse = {}", huankuanFindUrl, "", printJson(huankuanFindResult.getData()));
        } else {
            logger.info("查询  还款失败: {}", huankuanFindResult.getError());
            System.exit(-1);
        }

        // 4. 更新
        String huankuanUpdateUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/huankuans/" + huankuanCreateResult.getData().getId();
        huankuanCreateResult.getData().getHuankuanMapList().get(0).setFee(new BigDecimal("8888"));
        huankuanCreateResult.getData().getHuankuanMapList().get(0).setId(2L);
        huankuan.setHuankuanMapList(huankuanCreateResult.getData().getHuankuanMapList());
        huankuan.setId(huankuanCreateResult.getData().getId());
        Result<Integer> huankuanUpdateResult = client.exchange(huankuanUpdateUrl, HttpMethod.PUT, new HttpEntity<Huankuan>(huankuan), typeReferenceInteger).getBody();
        if (huankuanUpdateResult.getSuccess()) {
            logger.info("更新  还款成功\n  GET {}\nrequest = {}\nresponse = {}", huankuanUpdateUrl, printJson(huankuan), printJson(huankuanUpdateResult.getData()));
        } else {
            logger.info("更新  还款失败: {}", huankuanUpdateResult.getError());
            System.exit(-1);
        }
    }

    private void huankuanAuto()throws JsonProcessingException {
        //创建多笔借款

        String jiekuanCreateUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/jiekuans";
        Jiekuan jiekuanOne = new Jiekuan() {{
            setOrderId(yingOrderResult.getData().getId());
            setHsId(yingOrderConfigResult.getData().getId());
            setFukuanId(0L);
            setJiekuanDate(stringToTime("20170701"));
            setCapitalId(1L);
            setAmount(new BigDecimal("100.00"));
            setUseDays(35);
            setUseInterest(new BigDecimal("7.50"));

        }};
        Jiekuan jiekuanTwo = new Jiekuan() {{
            setOrderId(yingOrderResult.getData().getId());
            setHsId(yingOrderConfigResult.getData().getId());
            setFukuanId(0L);
            setJiekuanDate(stringToTime("20170702"));
            setCapitalId(1L);
            setAmount(new BigDecimal("200.00"));
            setUseDays(35);
            setUseInterest(new BigDecimal("7.50"));

        }};
        Jiekuan jiekuanThree = new Jiekuan() {{
            setOrderId(yingOrderResult.getData().getId());
            setHsId(yingOrderConfigResult.getData().getId());
            setFukuanId(0L);
            setJiekuanDate(stringToTime("20170703"));
            setCapitalId(1L);
            setAmount(new BigDecimal("300.00"));
            setUseDays(35);
            setUseInterest(new BigDecimal("7.50"));

        }};
        jiekuanCreateResult = client.exchange(jiekuanCreateUrl, HttpMethod.POST, new HttpEntity<>(jiekuanOne), typeReferenceJiekuan).getBody();

        client.exchange(jiekuanCreateUrl, HttpMethod.POST, new HttpEntity<>(jiekuanTwo), typeReferenceJiekuan).getBody();
        client.exchange(jiekuanCreateUrl, HttpMethod.POST, new HttpEntity<>(jiekuanThree), typeReferenceJiekuan).getBody();

        //查询还款尚未匹配完成借款
        String jiekuanListUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/jiekuansUnfinished" ;
        Result<List<Jiekuan>> jiekuanListResult = client.exchange(jiekuanListUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceJiekuanList).getBody();

        if (jiekuanListResult.getSuccess()) {
            logger.info("创建分页成功\n GET {}\nrequest = {}\nresponse = {}", jiekuanListResult, "", printJson(jiekuanListResult.getData()));
        } else {
            logger.info("创建分页失败: {}", jiekuanListResult.getError());
            System.exit(-1);
        }


        //还款

        String huankuanCreateUrl = "/api/business/cang/" + yingOrderResult.getData().getId() + "/huankuans";
        Huankuan huankuan = new Huankuan() {{



            setOrderId(yingOrderResult.getData().getId());
            setHsId(yingOrderConfigResult.getData().getId());
            setHuankuanDate(stringToTime("2017-7-26"));


        }};
        List<HuankuanMap> hukuanMapList = new ArrayList<>();

        Jiekuan jiekuan = jiekuanListResult.getData().get(0);

            HuankuanMap map = new HuankuanMap();
            map.setPrincipal(jiekuan.getAmount().subtract(new BigDecimal("50")));
            map.setInterest(new BigDecimal("1700.02"));
            map.setFee(new BigDecimal("570.0"));
            map.setJiekuanId(jiekuan.getId());
            hukuanMapList.add(map);

        huankuan.setHuankuanMapList(hukuanMapList);
        huankuanCreateResult = client.exchange(huankuanCreateUrl, HttpMethod.POST, new HttpEntity<>(huankuan), typeReferenceHuankuan).getBody();
        if (huankuanCreateResult.getSuccess()) {
            logger.info("创建还款成功\nPOST {}\nrequest = {}\nresponse = {}", huankuanCreateUrl, printJson(huankuan), printJson(huankuanCreateResult.getData()));
        } else {
            logger.info("创建还款失败: {}", huankuanCreateResult.getError());
            System.exit(-1);
        }
        List<HuankuanMap> hukuanMapList2 = new ArrayList<>();

        Jiekuan jiekuan2 = jiekuanListResult.getData().get(1);

        HuankuanMap map2 = new HuankuanMap();
        map.setPrincipal(jiekuan2.getAmount().subtract(new BigDecimal("50")));
        map.setInterest(new BigDecimal("1700.02"));
        map.setFee(new BigDecimal("570.0"));
        map.setJiekuanId(jiekuan2.getId());
        hukuanMapList2.add(map);
        huankuan.setHuankuanMapList(hukuanMapList2);
        huankuan.setHuankuanDate(stringToTime("20170809"));
        huankuanCreateResult = client.exchange(huankuanCreateUrl, HttpMethod.POST, new HttpEntity<>(huankuan), typeReferenceHuankuan).getBody();

    }

    private void fayun() throws JsonProcessingException {

        // 1. 添加发运
        String fayunCreateUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/fayuns";

        YingFayun fayun = new YingFayun() {{
            setOrderId(yingOrderResult.getData().getId());
            setDownstreamCars(166);
            setDownstreamTrafficMode(TrafficMode.MOTOR);
            setUpstreamTrafficMode(TrafficMode.RAIL);
            setUpstreamJHH("1000001");
            setDownstreamCars(1234);
            setFyAmount(new BigDecimal("1510.60"));
            setArriveStatus(CargoArriveStatus.UNARRIVE);
            setHsId(yingOrderConfigResult.getData().getId());
            setFyDate(stringToTime("2017-6-20"));
        }};

        fayunCreateResult = client.exchange(fayunCreateUrl, HttpMethod.POST, new HttpEntity<>(fayun), typeReferenceYingFayun).getBody();
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

        String fayunPageUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/fayuns?" + WebUtils.getUrlTemplate(PageYingFayunDTO.class);
        Result<Page<YingFayun>> fayunPageResult = client.exchange(fayunPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceYingFayunPage, variablesFayun).getBody();
        if (fayunPageResult.getSuccess()) {
            logger.info("发运分页成功\nGET {}\nrequest = {}\nresponse = {}", fayunPageUrl, variablesFayun, printJson(fayunPageResult.getData()));
        } else {
            logger.info("发运分页失败: {}", fayunPageResult.getError());
            System.exit(-1);
        }

        // 3. id查询
        String fayunFindUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/fayuns/" + fayunCreateResult.getData().getId();
        Result<YingFayun> fayunFindResult = client.exchange(fayunFindUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceYingFayun).getBody();
        if (fayunFindResult.getSuccess()) {
            logger.info("查询发运成功\nGET {}\nrequest = {}\nresponse = {}", fayunFindUrl, "", printJson(fayunFindResult.getData()));
        } else {
            logger.info("查询发运失败: {}", fayunFindResult.getError());
            System.exit(-1);
        }
    }

    public void ruku() throws JsonProcessingException {

        // 1. 添加入库
        String rukuCreateUrl = "/api/business/cang/" + yingOrderResult.getData().getId() + "/rukus";
        CangRuku ruku = new CangRuku() {{
            setHsId(yingOrderConfigResult.getData().getId());
            setOrderId(yingOrderResult.getData().getId());
            setLocality("泰德码头");
            setRukuAmount(new BigDecimal(32715));
            setRukuPrice(new BigDecimal(22581201.60));
            setTrafficMode(TrafficMode.SHIP);
            setShip("华盛15");
            setRukuStatus(InStorageStatus.IN_STORAGE);
            setRukuDate(LocalDateTime.of(2017, 3, 1, 0, 0));


        }};
        Result<CangRuku> rukuCreateResult = client.exchange(rukuCreateUrl, HttpMethod.POST, new HttpEntity<>(ruku), typeReferenceCangRuku).getBody();
        if (rukuCreateResult.getSuccess()) {
            logger.info("创建仓押入库成功\n POST {}\nrequest = {}\nresponse = {}", rukuCreateUrl, printJson(ruku), printJson(rukuCreateResult.getData()));
        } else {
            logger.info("创建仓押入库算失败: {}", rukuCreateResult.getError());
            System.exit(-1);
        }

//
//        // 2. 分页
        String rukuPageUrl = "/api/business/cang/" + yingOrderResult.getData().getId() + "/rukus?" + WebUtils.getUrlTemplate(PageCangRukuDTO.class);

        Map<String, Object> rukuVariables = WebUtils.getUrlVariables(PageCangRukuDTO.class);
        rukuVariables.put("orderId", yingOrderResult.getData().getId());
        rukuVariables.put("orderId", yingOrderConfigResult.getData().getId());
        rukuVariables.put("pageSize", 5);
        rukuVariables.put("pageNo", 1);


        Result<Page<CangRuku>> rukuPageResult = client.exchange(rukuPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceCangRukuPage, rukuVariables).getBody();
        if (rukuPageResult.getSuccess()) {
            logger.info("仓押入库分页成功\nGET {}\nrequest = {}\nresponse = {}", rukuPageUrl, "", printJson(rukuPageResult.getData()));
        } else {
            logger.info("仓押入库分页失败: {}", rukuPageResult.getError());
            System.exit(-1);
        }

        //3 findone
        String rukugetFindUrl = "/api/business/cang/" + yingOrderResult.getData().getId() + "/rukus/" + rukuCreateResult.getData().getId();

        Result<CangRuku> rukuFindResult = client.exchange(rukugetFindUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceCangRuku).getBody();
        if (rukuFindResult.getSuccess()) {
            logger.info("仓押入库查询成功\nGET {}\nrequest = {}\nresponse = {}", rukugetFindUrl, "", printJson(rukuFindResult.getData()));
        } else {
            logger.info("仓押入库查询失败: {}", rukuFindResult.getError());
            System.exit(-1);
        }


        // 4. 更新
        String rukuUpdateUrl = "/api/business/cang/" + yingOrderResult.getData().getId() + "/rukus/" + rukuCreateResult.getData().getId();
        ruku.setRukuAmount(new BigDecimal("9999"));
        ruku.setOrderId(yingOrderResult.getData().getId());
        Result<Integer> yingFayunUpdateResult = client.exchange(rukuUpdateUrl, HttpMethod.PUT, new HttpEntity<CangRuku>(ruku), typeReferenceInteger).getBody();
        if (yingFayunUpdateResult.getSuccess()) {
            logger.info("更新仓押入成功\nPOST {}\nrequest = {}\nresponse = {}", rukuUpdateUrl, printJson(ruku), printJson(yingFayunUpdateResult.getData()));
        } else {
            logger.error("更新仓押入失败: {}", yingFayunUpdateResult.getError());
            System.exit(-2);
        }

    }

    public void chuku() throws JsonProcessingException {

        // 1. 添加入库
        String chukuCreateUrl = "/api/business/cang/" + yingOrderResult.getData().getId() + "/chukus";
        CangChuku chuku = new CangChuku() {{
            setHsId(yingOrderConfigResult.getData().getId());
            setOrderId(yingOrderResult.getData().getId());
            setLocality("泰德码头");
            setChukuAmount(new BigDecimal("2175.00"));
            setChukuPrice(new BigDecimal("22581201.60"));
            setChukuDate(LocalDateTime.of(2017, 3, 1, 0, 0));


        }};
        Result<CangChuku> chukuCreateResult = client.exchange(chukuCreateUrl, HttpMethod.POST, new HttpEntity<CangChuku>(chuku), typeReferenceCangChuku).getBody();
        if (chukuCreateResult.getSuccess()) {
            logger.info("创建仓押出库成功\n POST {}\nrequest = {}\nresponse = {}", chukuCreateUrl, printJson(chuku), printJson(chukuCreateResult.getData()));
        } else {
            logger.info("创建仓押出库算失败: {}", chukuCreateResult.getError());
            System.exit(-1);
        }
        // 第二笔 出库
        chuku.setId(2l);
        chuku.setChukuPrice(new BigDecimal("2000000"));
        chuku.setChukuAmount(new BigDecimal("3000"));
        chuku.setChukuDate(LocalDateTime.of(2017, 3, 17, 0, 0));
        client.exchange(chukuCreateUrl, HttpMethod.POST, new HttpEntity<CangChuku>(chuku), typeReferenceCangChuku).getBody();

        chuku.setId(3l);
        chuku.setChukuPrice(new BigDecimal("600000"));
        chuku.setChukuAmount(new BigDecimal("900"));
        chuku.setChukuDate(LocalDateTime.of(2017, 3, 22, 0, 0));
        client.exchange(chukuCreateUrl, HttpMethod.POST, new HttpEntity<CangChuku>(chuku), typeReferenceCangChuku).getBody();

        String chukuPageUrl = "/api/business/cang/" + yingOrderResult.getData().getId() + "/chukus?" + WebUtils.getUrlTemplate(PageCangChukuDTO.class);
        Map<String, Object> chukuVariables = WebUtils.getUrlVariables(PageCangChukuDTO.class);
        chukuVariables.put("orderId", yingOrderResult.getData().getId());
        chukuVariables.put("pageSize", 5);
        chukuVariables.put("pageNo", 1);

        Result<Page<CangChuku>> cangChukuPageResult = client.exchange(chukuPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceCangChukuPage, chukuVariables).getBody();

        if (cangChukuPageResult.getSuccess()) {
            logger.info("创建仓押出库分页成功\n GET {}\nrequest = {}\nresponse = {}", chukuPageUrl, "", printJson(cangChukuPageResult.getData()));
        } else {
            logger.info("创建仓押出库分页失败: {}", chukuCreateResult.getError());
            System.exit(-1);
        }


        //3 findone
        String chukugetFindUrl = "/api/business/cang/" + yingOrderResult.getData().getId() + "/chukus/" + chukuCreateResult.getData().getId();

        Result<CangChuku> chukuFindResult = client.exchange(chukugetFindUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceCangChuku).getBody();
        if (chukuFindResult.getSuccess()) {
            logger.info("仓押出库查询成功\nGET {}\nrequest = {}\nresponse = {}", chukugetFindUrl, "", printJson(chukuFindResult.getData()));
        } else {
            logger.info("仓押出库查询失败: {}", chukuFindResult.getError());
            System.exit(-1);
        }


        // 4. 更新
        String rukuUpdateUrl = "/api/business/cang/" + yingOrderResult.getData().getId() + "/chukus/" + chukuCreateResult.getData().getId();
        chuku.setChukuAmount(new BigDecimal("9999"));
        chuku.setOrderId(yingOrderResult.getData().getId());
        Result<Integer> yingChukuUpdateResult = client.exchange(rukuUpdateUrl, HttpMethod.PUT, new HttpEntity<CangChuku>(chuku), typeReferenceInteger).getBody();
        if (yingChukuUpdateResult.getSuccess()) {
            logger.info("更新仓押出库成功\nPOST {}\nrequest = {}\nresponse = {}", rukuUpdateUrl, printJson(chuku), printJson(yingChukuUpdateResult.getData()));
        } else {
            logger.error("更新仓押出库失败: {}", yingChukuUpdateResult.getError());
            System.exit(-2);
        }
    }

    private void fukuan() throws JsonProcessingException {
        // 1. 添加付款
        String fukuanCreateUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/fukuans";
        Fukuan yingFukuan = new Fukuan() {{
            setOrderId(yingOrderResult.getData().getId());
            setHsId(yingOrderConfigResult.getData().getId());
            setPayDate(stringToTime("2017-7-6"));
            setReceiveCompanyId(yingOrderResult.getData().getUpstreamId());
            setPayUsage(PaymentPurpose.DEPOSITECASH);
            setPayAmount(new BigDecimal("510000"));
            setCapitalId(2L);
        }};
        Fukuan yingFukuantwo = new Fukuan() {{
            setOrderId(yingOrderResult.getData().getId());
            setHsId(yingOrderConfigResult.getData().getId());
            setPayDate(stringToTime("2017-8-10"));
            setReceiveCompanyId(yingOrderResult.getData().getUpstreamId());
            setPayUsage(PaymentPurpose.FIAL_PAYMENT);
            setPayAmount(new BigDecimal("54294.93"));
            setCapitalId(2L);
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
        String fukuanPageUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/fukuans?" + WebUtils.getUrlTemplate(PageFukuanDTO.class);

        Map<String, Object> fukuanVariablesPage = WebUtils.getUrlVariables(PageFukuanDTO.class);
        fukuanVariablesPage.put("orderId", yingOrderResult.getData().getId());
        fukuanVariablesPage.put("pageSize", 5);
        fukuanVariablesPage.put("pageNo", 1);


        Result<Page<Fukuan>> fukuanPageResult = client.exchange(fukuanPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceFukuanPage, fukuanVariablesPage).getBody();
        if (fukuanPageResult.getSuccess()) {
            logger.info("付款分页成功\nGET {}\nrequest = {}\nresponse = {}", fukuanPageUrl, "", printJson(fukuanPageResult.getData()));
        } else {
            logger.info("付款分页失败: {}", fukuanPageResult.getError());
            System.exit(-1);
        }

        // 3. id查询
        String fukuanFindUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/fukuans/" + fukuanResult.getData().getId();
        Result<Fukuan> fukuanFindResult = client.exchange(fukuanFindUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceFukuan).getBody();
        if (fukuanFindResult.getSuccess()) {
            logger.info("查询付款成功\nGET {}\nrequest = {}\nresponse = {}", fukuanFindUrl, "", printJson(fukuanFindResult.getData()));
        } else {
            logger.info("查询付款失败: {}", fukuanFindResult.getError());
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

    public void jiekuan_huankuan() throws JsonProcessingException{

        // 借款 - 分页

        String jiekuanPageUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/jiekuans?" + WebUtils.getUrlTemplate(PageJiekuanDTO.class);

        Map<String, Object> variablesHuikuan = WebUtils.getUrlVariables(PageJiekuanDTO.class);
        variablesHuikuan.put("orderId", yingOrderResult.getData().getId());
        variablesHuikuan.put("pageSize", 5);
        variablesHuikuan.put("pageNo", 1);


        Result<Page<Jiekuan>> jiekuanPageResult = client.exchange(jiekuanPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceJiekuanPage, variablesHuikuan).getBody();

        if (jiekuanPageResult.getSuccess()) {
            logger.info("创建分页成功\n GET {}\nrequest = {}\nresponse = {}", jiekuanPageUrl, "", printJson(jiekuanPageResult.getData()));
        } else {
            logger.info("创建分页失败: {}", jiekuanPageResult.getError());
            System.exit(-1);
        }

        // 3. id查询
        String jiekuanFindUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/jiekuans/" + jiekuanCreateResult.getData().getId();
        Result<Jiekuan> jiekuanFindResult = client.exchange(jiekuanFindUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceJiekuan).getBody();
        if (jiekuanFindResult.getSuccess()) {
            logger.info("查询借款成功\nGET {}\nrequest = {}\nresponse = {}", jiekuanFindUrl, "", printJson(jiekuanFindResult.getData()));
        } else {
            logger.info("查询借款失败: {}", jiekuanFindResult.getError());
            System.exit(-1);
        }

        Jiekuan jiekuan=jiekuanFindResult.getData();
        jiekuan.setAmount(new BigDecimal("6666666"));
//        // 4.更新
//        String jiekuanUpdateUrl = "/api/business/ying/" + yingOrderResult.getData().getId() + "/jiekuans/" + jiekuanCreateResult.getData().getId();
//        Result<Integer> jiekuanUpdateResult = client.exchange(jiekuanUpdateUrl, HttpMethod.PUT, new HttpEntity<>(jiekuan), typeReferenceInteger).getBody();
//        if (jiekuanUpdateResult.getSuccess()) {
//            logger.info("更新借款成功\nGET {}\nrequest = {}\nresponse = {}", jiekuanUpdateUrl, "", printJson(jiekuanUpdateResult.getData()));
//        } else {
//            logger.info("更新借款失败: {}", jiekuanUpdateResult.getError());
//            System.exit(-1);
//        }

    }



    public  static  boolean timeCompare(LocalDateTime startTime,LocalDateTime endTime){
        Duration duration = java.time.Duration.between(startTime,  endTime );
//        if (duration.toMillis()>1) {
//        }
        return false;
    }
}
