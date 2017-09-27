package com.yimei.hs.ying.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.hs.ying.entity.*;
import com.yimei.hs.ying.service.YingLogService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class YingLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(com.yimei.hs.ying.aop.YingLogAspect.class);

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
}
