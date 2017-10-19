package com.yimei.hs.ying.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.hs.same.service.LogService;
import com.yimei.hs.ying.entity.*;
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
    LogService logService;

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
        if (arg instanceof YingFayun) {
        } else if (arg instanceof YingBail) {
        }

        return;

    }

    /**
     *
     */
    @After("execution(* com.yimei.hs.ying.service..*.update*(..))")
    public void updateYing() {
        Object arg = null;
        if (arg instanceof YingFayun) {
        } else if (arg instanceof YingBail) {
        }
        return;
    }

    /**
     *
     */
    @After("execution(* com.yimei.hs.ying.service..*.delete(..))")
    public void deleteYing() {
        Object arg = null;
        if (arg instanceof YingFayun) {
        } else if (arg instanceof YingBail) {
        }
        return;
    }
}
