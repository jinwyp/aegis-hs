package com.yimei.hs.same.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.hs.same.entity.Fee;
import com.yimei.hs.same.entity.Invoice;
import com.yimei.hs.same.entity.Order;
import com.yimei.hs.same.entity.SettleTraffic;
import com.yimei.hs.same.entity.*;
import com.yimei.hs.same.service.LogService;
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

    /**
     * 创建memo
     */
    @After("execution(* com.yimei.hs.same.service..*.create(..))")
    public void create() {
        // todo
        // 1.  找出切点的参数,
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
