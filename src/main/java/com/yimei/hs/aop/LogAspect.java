package com.yimei.hs.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.hs.ying.entity.*;
import com.yimei.hs.ying.service.YingLogService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hary on 2017/9/22.
 */

@Aspect
@Configuration
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    YingLogService yingLogService;

    @Autowired
    ObjectMapper om;

    /**
     *
     */
    @After("execution(* com.yimei.hs.ying.service..*.create(..))")
    public void createYing() {
        // todo
        // 1.  找出切点的参数,
        Object arg = null;
        if (arg instanceof YingOrder) {
        } else if (arg instanceof YingFayun) {
        } else if (arg instanceof YingFukuan) {
        } else if (arg instanceof YingHuikuan) {
        } else if (arg instanceof YingHuankuan) {
        } else if (arg instanceof YingSettleDownstream) {
        } else if (arg instanceof YingSettleUpstream) {
        } else if (arg instanceof YingSettleTraffic) {
        } else if (arg instanceof YingFee) {
        } else if (arg instanceof YingInvoice) {
        }

        return;

    }

    /**
     *
     */
    @After("execution(* com.yimei.hs.ying.service..*.update*(..))")
    public void updateYing() {
        Object arg = null;
        if (arg instanceof YingOrder) {
        } else if (arg instanceof YingFayun) {
        } else if (arg instanceof YingFukuan) {
        } else if (arg instanceof YingHuikuan) {
        } else if (arg instanceof YingHuankuan) {
        } else if (arg instanceof YingSettleDownstream) {
        } else if (arg instanceof YingSettleUpstream) {
        } else if (arg instanceof YingSettleTraffic) {
        } else if (arg instanceof YingFee) {
        } else if (arg instanceof YingInvoice) {
        }
        return;
    }


    /**
     *
     */
    @After("execution(* com.yimei.hs.cang.service..*.create(..))")
    public void createCang() {
        // todo
        // 1.  找出切点的参数,
        Object arg = null;
        if (arg instanceof YingOrder) {
        } else if (arg instanceof YingFayun) {
        } else if (arg instanceof YingFukuan) {
        } else if (arg instanceof YingHuikuan) {
        } else if (arg instanceof YingHuankuan) {
        } else if (arg instanceof YingSettleDownstream) {
        } else if (arg instanceof YingSettleUpstream) {
        } else if (arg instanceof YingSettleTraffic) {
        } else if (arg instanceof YingFee) {
        } else if (arg instanceof YingInvoice) {
        }
    }


    /**
     *
     */
    @After("execution(* com.yimei.hs.cang.service..*.update*(..))")
    public void updateCang() {
        Object arg = null;
        if (arg instanceof YingOrder) {
        } else if (arg instanceof YingFayun) {
        } else if (arg instanceof YingFukuan) {
        } else if (arg instanceof YingHuikuan) {
        } else if (arg instanceof YingHuankuan) {
        } else if (arg instanceof YingSettleDownstream) {
        } else if (arg instanceof YingSettleUpstream) {
        } else if (arg instanceof YingSettleTraffic) {
        } else if (arg instanceof YingFee) {
        } else if (arg instanceof YingInvoice) {
        }
        return;
    }
}

