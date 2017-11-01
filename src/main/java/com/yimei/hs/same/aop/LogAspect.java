package com.yimei.hs.same.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.hs.boot.api.Logutil;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.enums.EntityType;
import com.yimei.hs.same.entity.Fee;
import com.yimei.hs.same.entity.Invoice;
import com.yimei.hs.same.entity.Order;
import com.yimei.hs.same.entity.SettleTraffic;
import com.yimei.hs.same.entity.*;
import com.yimei.hs.same.mapper.OrderMapper;
import com.yimei.hs.same.service.LogService;
import com.yimei.hs.ying.entity.YingFayun;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(com.yimei.hs.same.aop.LogAspect.class);

    @Autowired
    LogService logService;

    @Autowired
    ObjectMapper om;
    @Autowired
    private OrderMapper orderMapper;
    /**
     * 创建memo
     */
    @After("execution(* com.yimei.hs.same.service..*.create(..))")
    public void create(JoinPoint joinPoint) throws  Exception{
        Object arg = (joinPoint.getArgs().length > 0 ? joinPoint.getArgs()[0] : null);

        if (arg instanceof Order) {
            Order  order= (Order) arg;
            Logutil.create(om, orderMapper, logService, order,(order.getBusinessType().equals(BusinessType.ying)?EntityType.yingOrderInsert:EntityType.cangOrderInsert));
        } else if (arg instanceof SettleTraffic) {
            SettleTraffic settleTraffic = (SettleTraffic) arg;
            Logutil.create(om, orderMapper, logService, settleTraffic,EntityType.cangSettleTrafficInsert);
        } else if (arg instanceof Fee) {
            Fee fee = (Fee) arg;
            Order order = orderMapper.selectByPrimaryKey(fee.getOrderId());
            Logutil.create(om, orderMapper, logService, fee,(order.getBusinessType().equals(BusinessType.ying)?EntityType.yingFeeInsert:EntityType.cangFeeInsert));
        } else if (arg instanceof OrderConfig) {
            OrderConfig orderConfig = (OrderConfig) arg;
            Order order = orderMapper.selectByPrimaryKey(orderConfig.getOrderId());
            Logutil.create(om, orderMapper, logService, orderConfig,(order.getBusinessType().equals(BusinessType.ying)?EntityType.yingConfigInsert:EntityType.cangOrderInsert));
        } else if (arg instanceof OrderParty) {
            OrderParty orderParty = (OrderParty) arg;
            Order order = orderMapper.selectByPrimaryKey(orderParty.getOrderId());
            Logutil.create(om, orderMapper, logService, orderParty,(order.getBusinessType().equals(BusinessType.ying)?EntityType.yingPartyInsert:EntityType.cangPartyInsert));
        } else if (arg instanceof Invoice) {
            Invoice invoice = (Invoice) arg;
            Order order = orderMapper.selectByPrimaryKey(invoice.getOrderId());
            Logutil.create(om, orderMapper, logService, invoice,(order.getBusinessType().equals(BusinessType.ying)?EntityType.yingInvoiceInsert:EntityType.cangInvoiceInsert));
        } else if (arg instanceof InvoiceDetail) {

            InvoiceDetail invoiceDetail = (InvoiceDetail) arg;
//            Order order = orderMapper.selectByPrimaryKey(invoiceDetail.getOrderId());
//            Logutil.create(om, orderMapper, logService, invoiceDetail,(order.getBusinessType().equals(BusinessType.ying)?EntityType.yingConfigInsert:EntityType.cangOrderInsert));
        } else if (arg instanceof Huikuan) {

            Huikuan huikuan = (Huikuan) arg;
            Order order = orderMapper.selectByPrimaryKey(huikuan.getOrderId());
            Logutil.create(om, orderMapper, logService, huikuan,(order.getBusinessType().equals(BusinessType.ying)?EntityType.yingHuikuanInsert:EntityType.cangHuankuanInsert));

        } else if (arg instanceof Huankuan) {

            Huankuan huankuan = (Huankuan) arg;
            Order order = orderMapper.selectByPrimaryKey(huankuan.getOrderId());
            Logutil.create(om, orderMapper, logService, huankuan,(order.getBusinessType().equals(BusinessType.ying)?EntityType.yingHuankuanInsert:EntityType.cangHuankuanInsert));

        }else if (arg instanceof Jiekuan) {

            Jiekuan jiekuan = (Jiekuan) arg;
            Order order = orderMapper.selectByPrimaryKey(jiekuan.getOrderId());
            Logutil.create(om, orderMapper, logService, jiekuan,(order.getBusinessType().equals(BusinessType.ying)?EntityType.yingJiekuanInsert:EntityType.cangJiekuanInsert));

        }else if (arg instanceof Fukuan) {

            Fukuan fukuan = (Fukuan) arg;
            Order order = orderMapper.selectByPrimaryKey(fukuan.getOrderId());
            Logutil.create(om, orderMapper, logService, fukuan,(order.getBusinessType().equals(BusinessType.ying)?EntityType.yingFukuanInsert:EntityType.cangFukuanInsert));

        }else if (arg instanceof SettleSeller) {

            SettleSeller settleSeller = (SettleSeller) arg;
            Order order = orderMapper.selectByPrimaryKey(settleSeller.getOrderId());
            Logutil.create(om, orderMapper, logService, settleSeller,(order.getBusinessType().equals(BusinessType.ying)?EntityType.yingSettleUpstreamInsert:EntityType.cangSettleUpstreamInsert));

        }else if (arg instanceof SettleBuyer) {
            SettleBuyer settleBuyer = (SettleBuyer) arg;
            Order order = orderMapper.selectByPrimaryKey(settleBuyer.getOrderId());
            Logutil.create(om, orderMapper, logService, settleBuyer,(order.getBusinessType().equals(BusinessType.ying)?EntityType.yingSettleDownInsert:EntityType.cangSettleDownInsert));

        }
        return;

    }

    /**
     * 更新memo
     */
    @After("execution(* com.yimei.hs.same.service..*.update*(..))")
    public void update() {
        Object arg = null;
        if (arg instanceof Order) {
        } else if (arg instanceof SettleTraffic) {
        } else if (arg instanceof Fee) {
        } else if (arg instanceof OrderConfig) {
        } else if (arg instanceof OrderParty) {
        } else if (arg instanceof Invoice) {
        } else if (arg instanceof InvoiceDetail) {
        }
        return;
    }

    /**
     * 删除memo
     */
    @After("execution(* com.yimei.hs.same.service..*.delete(..))")
    public void delete() {
        Object arg = null;
        if (arg instanceof Order) {
        } else if (arg instanceof SettleTraffic) {
        } else if (arg instanceof Fee) {
        } else if (arg instanceof OrderConfig) {
        } else if (arg instanceof OrderParty) {
        } else if (arg instanceof Invoice) {
        } else if (arg instanceof InvoiceDetail) {
        }
        return;
    }
}
