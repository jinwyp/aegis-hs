package com.yimei.hs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.cang.dto.PageCangChukuDTO;
import com.yimei.hs.cang.dto.PageCangRukuDTO;
import com.yimei.hs.cang.entity.CangChuku;
import com.yimei.hs.cang.entity.CangRuku;
import com.yimei.hs.enums.*;
import com.yimei.hs.same.dto.PageFeeDTO;
import com.yimei.hs.same.dto.PageOrderConfigDTO;
import com.yimei.hs.same.dto.PageOrderDTO;
import com.yimei.hs.same.dto.PageSettleTrafficDTO;
import com.yimei.hs.same.entity.*;
import com.yimei.hs.test.HsTestBase;
import com.yimei.hs.user.entity.Dept;
import com.yimei.hs.user.entity.Party;
import com.yimei.hs.user.entity.Team;
import com.yimei.hs.user.entity.User;
import com.yimei.hs.util.WebUtils;
import com.yimei.hs.ying.dto.PageYingFeeDTO;
import com.yimei.hs.ying.dto.PageYingOrderConfigDTO;
import com.yimei.hs.ying.dto.PageYingSettleTrafficDTO;
import com.yimei.hs.ying.dto.PageYingTransferDTO;
import com.yimei.hs.ying.entity.YingOrderConfig;
import com.yimei.hs.ying.entity.YingSettleTraffic;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

//    Result<SettleDownstream> downstreamCreateResult = null;
//    Result<SettleUpstream> upstreamCreateResult = null;
//    Result<Fukuan> fukuanResult = null;
//    Result<Huankuan> huankuanCreateResult = null;
//    Result<Huikuan> huikuanCreateResult = null;
//    Result<Fayun> fayunCreateResult = null;

    @Test
    public void yingIntegration() throws JsonProcessingException {
        System.out.println("开始应收集成测试");
        defaultUser();
        order();
        config();
        fee();
        traffic();

        ruku();
        chuku();

    }

    public void order() throws JsonProcessingException {
        // 7. 创建应收订单
        Order yingOrder = new Order();
        yingOrder.setBusinessType(BusinessType.ying);
        yingOrder.setTeamId(1L); // 赵善文团队
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

    public void ruku() throws JsonProcessingException {

        // 1. 添加入库
        String rukuCreateUrl = "/api/cang/" + yingOrderResult.getData().getId() + "/rukus";
        CangRuku ruku = new CangRuku() {{
            setHsId(yingOrderConfigResult.getData().getId());
            setOrderId(yingOrderResult.getData().getId());
            setLocality("泰德码头");
            setRukuAmount(new BigDecimal(32715));
            setRukuPrice(new BigDecimal(22581201.60));
            setTrafficMode(TrafficMode.SHIP);
            setShip("华盛15");
            setRukuStatus(InStorageStatus.IN_STORAGE);
            setRukuDate(LocalDateTime.of(2017,3,1,0,0));


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
//        String rukuPageUrl = "/api/cang/" + yingOrderResult.getData().getId() + "/rukus?" + WebUtils.getUrlTemplate(PageCangRukuDTO.class);
//
//        Map<String, Object> rukuVariables = WebUtils.getUrlVariables(PageCangRukuDTO.class);
//        rukuVariables.put("orderId", yingOrderResult.getData().getId());
//        rukuVariables.put("orderId", yingOrderConfigResult.getData().getId());
//        rukuVariables.put("pageSize", 5);
//        rukuVariables.put("pageNo", 1);
//
//
//        Result<Page<CangRuku>> rukuPageResult = client.exchange(rukuPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceCangRukuPage, rukuVariables).getBody();
//        if (rukuPageResult.getSuccess()) {
//            logger.info("仓押入库分页成功\nGET {}\nrequest = {}\nresponse = {}", rukuPageUrl, "", printJson(rukuPageResult.getData()));
//        } else {
//            logger.info("仓押入库分页失败: {}", rukuPageResult.getError());
//            System.exit(-1);
//        }

        //3 findone
        String rukugetFindUrl = "/api/cang/" + yingOrderResult.getData().getId() + "/rukus/" + rukuCreateResult.getData().getId();

        Result <CangRuku> rukuFindResult = client.exchange(rukugetFindUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceCangRuku).getBody();
        if (rukuFindResult.getSuccess()) {
            logger.info("仓押入库查询成功\nGET {}\nrequest = {}\nresponse = {}", rukugetFindUrl, "", printJson(rukuFindResult.getData()));
        } else {
            logger.info("仓押入库查询失败: {}", rukuFindResult.getError());
            System.exit(-1);
        }



        // 4. 更新
        String rukuUpdateUrl = "/api/cang/" + yingOrderResult.getData().getId() + "/rukus/" + rukuCreateResult.getData().getId();
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
        String chukuCreateUrl = "/api/cang/" + yingOrderResult.getData().getId() + "/chukus";
        CangChuku chuku = new CangChuku() {{
            setHsId(yingOrderConfigResult.getData().getId());
            setOrderId(yingOrderResult.getData().getId());
            setLocality("泰德码头");
            setChukuAmount(new BigDecimal("2175.00"));
            setChukuPrice(new BigDecimal("22581201.60"));
            setChukuDate(LocalDateTime.of(2017,3,1,0,0));


        }};
        Result<CangChuku> chukuCreateResult = client.exchange(chukuCreateUrl, HttpMethod.POST, new HttpEntity<CangChuku>(chuku), typeReferenceCangChuku).getBody();
        if (chukuCreateResult.getSuccess()) {
            logger.info("创建仓押出库成功\n POST {}\nrequest = {}\nresponse = {}", chukuCreateUrl, printJson(chuku), printJson(chukuCreateResult.getData()));
        } else {
            logger.info("创建仓押出库算失败: {}", chukuCreateResult.getError());
            System.exit(-1);
        }
        // 第二笔 出库
//        chuku.setId(2l);
//        chuku.setChukuPrice(new BigDecimal("2000000"));
//        chuku.setChukuAmount(new BigDecimal("3000"));
//        chuku.setChukuDate(LocalDateTime.of(2017,3,17,0,0));
//        client.exchange(chukuCreateUrl, HttpMethod.POST, new HttpEntity<CangChuku>(chuku), typeReferenceCangChuku).getBody();
//
//        chuku.setId(3l);
//        chuku.setChukuPrice(new BigDecimal("600000"));
//        chuku.setChukuAmount(new BigDecimal("900"));
//        chuku.setChukuDate(LocalDateTime.of(2017,3,22,0,0));
//        client.exchange(chukuCreateUrl, HttpMethod.POST, new HttpEntity<CangChuku>(chuku), typeReferenceCangChuku).getBody();

//        String chukuPageUrl = "/api/cang/" + yingOrderResult.getData().getId() + "/chukus?" + WebUtils.getUrlTemplate(PageCangChukuDTO.class);
//        Map<String, Object> chukuVariables = WebUtils.getUrlVariables(PageCangChukuDTO.class);
//        chukuVariables.put("orderId", yingOrderResult.getData().getId());
//        chukuVariables.put("pageSize", 5);
//        chukuVariables.put("pageNo", 1);
//
//        Result<Page<CangChuku>> cangChukuPageResult = client.exchange(chukuPageUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceCangChukuPage, chukuVariables).getBody();
//
//        if (cangChukuPageResult.getSuccess()) {
//            logger.info("创建仓押出库分页成功\n POST {}\nrequest = {}\nresponse = {}", chukuPageUrl,"", printJson(cangChukuPageResult.getData()));
//        } else {
//            logger.info("创建仓押出库分页失败: {}", chukuCreateResult.getError());
//            System.exit(-1);
//        }



        //3 findone
        String chukugetFindUrl = "/api/cang/" + yingOrderResult.getData().getId() + "/chukus/" + chukuCreateResult.getData().getId();

        Result <CangChuku> chukuFindResult = client.exchange(chukugetFindUrl, HttpMethod.GET, HttpEntity.EMPTY, typeReferenceCangChuku).getBody();
        if (chukuFindResult.getSuccess()) {
            logger.info("仓押入库查询成功\nGET {}\nrequest = {}\nresponse = {}", chukugetFindUrl, "", printJson(chukuFindResult.getData()));
        } else {
            logger.info("仓押入库查询失败: {}", chukuFindResult.getError());
            System.exit(-1);
        }



        // 4. 更新
        String rukuUpdateUrl = "/api/cang/" + yingOrderResult.getData().getId() + "/chukus/" + chukuCreateResult.getData().getId();
        chuku.setChukuAmount(new BigDecimal("9999"));
        chuku.setOrderId(yingOrderResult.getData().getId());
        Result<Integer> yingChukuUpdateResult = client.exchange(rukuUpdateUrl, HttpMethod.PUT, new HttpEntity<CangChuku>(chuku), typeReferenceInteger).getBody();
        if (yingChukuUpdateResult.getSuccess()) {
            logger.info("更新仓押入成功\nPOST {}\nrequest = {}\nresponse = {}", rukuUpdateUrl, printJson(chuku), printJson(yingChukuUpdateResult.getData()));
        } else {
            logger.error("更新仓押入失败: {}", yingChukuUpdateResult.getError());
            System.exit(-2);
        }
    }
}
