package com.yimei.hs.same.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.enums.EntityType;
import com.yimei.hs.same.entity.Fee;
import com.yimei.hs.same.entity.Invoice;
import com.yimei.hs.same.entity.Order;
import com.yimei.hs.same.entity.SettleTraffic;
import com.yimei.hs.same.entity.*;
import com.yimei.hs.same.mapper.*;
import com.yimei.hs.same.service.*;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class LogAspect<T> {

    private static final Logger logger = LoggerFactory.getLogger(com.yimei.hs.same.aop.LogAspect.class);

    @Autowired
    LogService logService;

    @Autowired
    ObjectMapper om;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private SettleTrafficMapper settleTrafficMapper;
    @Autowired
    private FeeMapper feeMapper;
    @Autowired
    private OrderConfigMapper orderConfigMapper;
    @Autowired
    private OrderPartyMapper orderPartyMapper;
    @Autowired
    private InvoiceMapper invoiceMapper;

    @Autowired
    private HuankuanMapper huankuanMapper;

    @Autowired
    private HuikuanMapper huikuanMapper;

    @Autowired
    private FukuanMapper fukuanMapper;

    @Autowired
    private JiekuanMapper jiekuanMapper;

    @Autowired
    private SettleBuyerMapper settleBuyerMapper;
    @Autowired
    private SettleSellerMapper settleSellerMapper;

    @Autowired
    private TransferMapper transferMapper;


    /**
     * 创建memo
     */
    @After("execution(* com.yimei.hs.same.service..*.create(..))")
    public void create(JoinPoint joinPoint) {
        Object arg = (joinPoint.getArgs().length > 0 ? joinPoint.getArgs()[0] : null);

        if (arg instanceof Order) {
            Order order = (Order) arg;
            logService.createforOrder(order, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingOrderInsert : EntityType.cangOrderInsert));
        } else if (arg instanceof SettleTraffic) {
            SettleTraffic settleTraffic = (SettleTraffic) arg;
            logService.createLog(settleTraffic, EntityType.cangSettleTrafficInsert);
        } else if (arg instanceof Fee) {
            Fee fee = (Fee) arg;
            Order order = orderMapper.selectByPrimaryKey(fee.getOrderId());
            logService.createLog(fee, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingFeeInsert : EntityType.cangFeeInsert));
        } else if (arg instanceof OrderConfig) {
            OrderConfig orderConfig = (OrderConfig) arg;
            Order order = orderMapper.selectByPrimaryKey(orderConfig.getOrderId());
            logService.createforConfig(
                    orderConfig,
                    (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingConfigInsert : EntityType.cangOrderInsert));
        } else if (arg instanceof OrderParty) {
            OrderParty orderParty = (OrderParty) arg;
            Order order = orderMapper.selectByPrimaryKey(orderParty.getOrderId());
            logService.createLog(orderParty, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingPartyInsert : EntityType.cangPartyInsert));
        } else if (arg instanceof Invoice) {
            Invoice invoice = (Invoice) arg;
            Order order = orderMapper.selectByPrimaryKey(invoice.getOrderId());
            logService.createLog(invoice, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingInvoiceInsert : EntityType.cangInvoiceInsert));
        } else if (arg instanceof InvoiceDetail) {

            InvoiceDetail invoiceDetail = (InvoiceDetail) arg;
//            Order order = orderMapper.selectByPrimaryKey(invoiceDetail.getOrderId());
//            logService.createLog(om, orderMapper, logService, invoiceDetail,(order.getBusinessType().equals(BusinessType.ying)?EntityType.yingConfigInsert:EntityType.cangOrderInsert));
        } else if (arg instanceof Huikuan) {

            Huikuan huikuan = (Huikuan) arg;
            Order order = orderMapper.selectByPrimaryKey(huikuan.getOrderId());
            logService.createLog(huikuan, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingHuikuanInsert : EntityType.cangHuankuanInsert));

        } else if (arg instanceof Huankuan) {

            Huankuan huankuan = (Huankuan) arg;
            Order order = orderMapper.selectByPrimaryKey(huankuan.getOrderId());
            logService.createLog(huankuan, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingHuankuanInsert : EntityType.cangHuankuanInsert));

        } else if (arg instanceof Jiekuan) {

            Jiekuan jiekuan = (Jiekuan) arg;
            Order order = orderMapper.selectByPrimaryKey(jiekuan.getOrderId());
            logService.createLog(jiekuan, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingJiekuanInsert : EntityType.cangJiekuanInsert));

        } else if (arg instanceof Fukuan) {
            int rtn = 0;

            Fukuan fukuan = (Fukuan) arg;

            if (rtn == 1) {
                fukuan = null;

            }if (fukuan == null) {
                return;
            }
            logger.debug("日志： {}", fukuan.toString());
            Order order = orderMapper.selectByPrimaryKey(fukuan.getOrderId());
            rtn = logService.createLog(fukuan, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingFukuanInsert : EntityType.cangFukuanInsert));


        } else if (arg instanceof SettleSeller) {

            SettleSeller settleSeller = (SettleSeller) arg;
            Order order = orderMapper.selectByPrimaryKey(settleSeller.getOrderId());
            logService.createLog(settleSeller, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingSettleUpstreamInsert : EntityType.cangSettleUpstreamInsert));

        } else if (arg instanceof SettleBuyer) {
            SettleBuyer settleBuyer = (SettleBuyer) arg;
            Order order = orderMapper.selectByPrimaryKey(settleBuyer.getOrderId());
            logService.createLog(settleBuyer, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingSettleDownInsert : EntityType.cangSettleDownInsert));

        }
        return;

    }

    /**
     * 更新memo
     */
    @After("execution(* com.yimei.hs.same.service..*.update*(..))")
    public void update(JoinPoint joinPoint) {
        Object arg = (joinPoint.getArgs().length > 0 ? joinPoint.getArgs()[0] : null);
        if (arg instanceof Order) {
            Order order = (Order) arg;
            order.setOwnerId(orderMapper.selectByPrimaryKey(order.getId()).getOwnerId());
            logService.createforOrder(
                    order,
                    (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingOrderUpdate : EntityType.cangOrderUpdate));
        } else if (arg instanceof SettleTraffic) {
            SettleTraffic settleTraffic = (SettleTraffic) arg;
            Order order = orderMapper.selectByPrimaryKey(settleTraffic.getOrderId());
            logService.createLog(settleTraffic, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingSettleTrafficUpdate : EntityType.cangSettleTrafficUpdate));
        } else if (arg instanceof Fee) {
            Fee fee = (Fee) arg;
            Order order = orderMapper.selectByPrimaryKey(fee.getOrderId());
            logService.createLog(fee, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingFeeUpdate : EntityType.cangFeeUpdate));
        } else if (arg instanceof OrderConfig) {
            OrderConfig orderConfig = (OrderConfig) arg;
            Order order = orderMapper.selectByPrimaryKey(orderConfig.getOrderId());
            logService.createforConfig(
                    orderConfig,
                    (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingConfigUpdate : EntityType.cangOrderUpdate));
        } else if (arg instanceof OrderParty) {
            OrderParty orderParty = (OrderParty) arg;
            Order order = orderMapper.selectByPrimaryKey(orderParty.getOrderId());
            logService.createLog(orderParty, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingPartyUpdate : EntityType.cangPartyUpdate));
        } else if (arg instanceof Invoice) {
            Invoice invoice = (Invoice) arg;
            Order order = orderMapper.selectByPrimaryKey(invoice.getOrderId());
            logService.createLog(invoice, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingInvoiceUpdate : EntityType.cangInvoiceUpdate));
        } else if (arg instanceof InvoiceDetail) {

            InvoiceDetail invoiceDetail = (InvoiceDetail) arg;
//            Order order = orderMapper.selectByPrimaryKey(invoiceDetail.getOrderId());
//            logService.createLog(om, orderMapper, logService, invoiceDetail,(order.getBusinessType().equals(BusinessType.ying)?EntityType.yingConfigUpdate:EntityType.cangOrderUpdate));
        } else if (arg instanceof Huikuan) {

            Huikuan huikuan = (Huikuan) arg;
            Order order = orderMapper.selectByPrimaryKey(huikuan.getOrderId());
            logService.createLog(huikuan, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingHuikuanUpdate : EntityType.cangHuankuanUpdate));

        } else if (arg instanceof Huankuan) {

            Huankuan huankuan = (Huankuan) arg;
            Order order = orderMapper.selectByPrimaryKey(huankuan.getOrderId());
            logService.createLog(huankuan, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingHuankuanUpdate : EntityType.cangHuankuanUpdate));

        } else if (arg instanceof Jiekuan) {

            Jiekuan jiekuan = (Jiekuan) arg;
            Order order = orderMapper.selectByPrimaryKey(jiekuan.getOrderId());
            logService.createLog(jiekuan, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingJiekuanUpdate : EntityType.cangJiekuanUpdate));

        } else if (arg instanceof Fukuan) {

            Fukuan fukuan = (Fukuan) arg;
            Order order = orderMapper.selectByPrimaryKey(fukuan.getOrderId());
            logService.createLog(fukuan, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingFukuanUpdate : EntityType.cangFukuanUpdate));

        } else if (arg instanceof SettleSeller) {

            SettleSeller settleSeller = (SettleSeller) arg;
            Order order = orderMapper.selectByPrimaryKey(settleSeller.getOrderId());
            logService.createLog(settleSeller, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingSettleUpstreamUpdate : EntityType.cangSettleUpstreamUpdate));

        } else if (arg instanceof SettleBuyer) {
            SettleBuyer settleBuyer = (SettleBuyer) arg;
            Order order = orderMapper.selectByPrimaryKey(settleBuyer.getOrderId());
            logService.createLog(settleBuyer, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingSettleDownUpdate : EntityType.cangSettleDownUpdate));
        }
    }

    /**
     * 删除memo
     */
    @After("execution(* com.yimei.hs.same.service..*.delete(..))")
    public void delete(JoinPoint joinPoint) {
        joinPoint.getArgs();
        Long id = (Long) joinPoint.getArgs()[0];

        String fileName = joinPoint.getSignature().getDeclaringTypeName();
        if (fileName.equals(OrderService.class.getName())) {
            Order order = orderMapper.selectByPrimaryKeyDeleted(id);
            logService.createforOrder(order, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingOrderDel : EntityType.cangOrderDel));
        } else if (fileName.equals(SettleTrafficService.class.getName())) {
            SettleTraffic settleTraffic = settleTrafficMapper.selectByPrimaryKeyDeleted(id);
            Order order = orderMapper.selectByPrimaryKey(settleTraffic.getOrderId());
            logService.createLog(settleTraffic, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingSettleTrafficDel : EntityType.cangSettleTrafficDel));
        } else if (fileName.equals(FeeService.class.getName())) {
            Fee fee = feeMapper.selectByPrimaryKeyDeleted(id);
            Order order = orderMapper.selectByPrimaryKey(fee.getOrderId());
            logService.createLog(fee, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingFeeDel : EntityType.cangFeeDel));
        } else if (fileName.equals(OrderConfigService.class.getName())) {
            OrderConfig orderConfig = orderConfigMapper.selectByPrimaryKeyDeleted(id);
            Order order = orderMapper.selectByPrimaryKey(orderConfig.getOrderId());
            logService.createforConfig(
                    orderConfig,
                    (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingConfigDel : EntityType.cangOrderDel));
        } else if (fileName.equals(OrderPartyService.class.getName())) {
            OrderParty orderParty = orderPartyMapper.selectByPrimaryKeyDeleted(id);
            Order order = orderMapper.selectByPrimaryKey(orderParty.getOrderId());
            logService.createLog(orderParty, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingPartyDel : EntityType.cangPartyDel));
        } else if (fileName.equals(InvoiceService.class.getName())) {
            Invoice invoice = invoiceMapper.selectByPrimaryKeyDeleted(id);
            Order order = orderMapper.selectByPrimaryKey(invoice.getOrderId());
            logService.createLog(invoice, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingInvoiceDel : EntityType.cangInvoiceDel));
        } else if (fileName.equals(HuikuanService.class.getName())) {

            Huikuan huikuan = huikuanMapper.selectByPrimaryKeyDeleted(id);
            Order order = orderMapper.selectByPrimaryKey(huikuan.getOrderId());
            logService.createLog(huikuan, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingHuikuanDel : EntityType.cangHuankuanDel));

        } else if (fileName.equals(HuankuanService.class.getName())) {

            Huankuan huankuan = huankuanMapper.selectByPrimaryKeyDeleted(id);
            Order order = orderMapper.selectByPrimaryKey(huankuan.getOrderId());
            logService.createLog(huankuan, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingHuankuanDel : EntityType.cangHuankuanDel));

        } else if (fileName.equals(JiekuanService.class.getName())) {

            Jiekuan jiekuan = jiekuanMapper.selectByPrimaryKeyDeleted(id);
            Order order = orderMapper.selectByPrimaryKey(jiekuan.getOrderId());
            logService.createLog(jiekuan, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingJiekuanDel : EntityType.cangJiekuanDel));

        } else if (fileName.equals(FukuanService.class.getName())) {

            Fukuan fukuan = fukuanMapper.selectByPrimaryKeyDeleted(id);
            Order order = orderMapper.selectByPrimaryKey(fukuan.getOrderId());
            logService.createLog(fukuan, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingFukuanDel : EntityType.cangFukuanDel));

        } else if (fileName.equals(SettleSellerService.class.getName())) {

            SettleSeller settleSeller = settleSellerMapper.selectByPrimaryKeyDeleted(id);
            Order order = orderMapper.selectByPrimaryKey(settleSeller.getOrderId());
            logService.createLog(settleSeller, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingSettleUpstreamDel : EntityType.cangSettleUpstreamDel));

        } else if (fileName.equals(SettleBuyerService.class.getName())) {
            SettleBuyer settleBuyer = settleBuyerMapper.selectByPrimaryKeyDeleted(id);
            Order order = orderMapper.selectByPrimaryKey(settleBuyer.getOrderId());
            logService.createLog(settleBuyer, (order.getBusinessType().equals(BusinessType.ying) ? EntityType.yingSettleDownDel : EntityType.cangSettleDownDel));
        }
    }
}
