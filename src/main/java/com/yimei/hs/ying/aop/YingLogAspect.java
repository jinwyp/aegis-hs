package com.yimei.hs.ying.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.hs.boot.api.Logutil;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.enums.EntityType;
import com.yimei.hs.same.entity.Log;
import com.yimei.hs.same.entity.Order;
import com.yimei.hs.same.mapper.OrderMapper;
import com.yimei.hs.same.service.LogService;
import com.yimei.hs.ying.entity.*;
import com.yimei.hs.ying.mapper.YingBailMapper;
import com.yimei.hs.ying.mapper.YingFayunMapper;
import com.yimei.hs.ying.service.YingBailService;
import com.yimei.hs.ying.service.YingFayunService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Arrays;

@Aspect
@Configuration
public class YingLogAspect<T> {

    private static final Logger logger = LoggerFactory.getLogger(com.yimei.hs.ying.aop.YingLogAspect.class);

    @Autowired
    LogService logService;

    @Autowired
    ObjectMapper om;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private YingFayunMapper yingFayunMapper;
    @Autowired
    private YingBailMapper yingBailMapper;

    /**
     *
     */
    @After("execution(* com.yimei.hs.ying.service..*.create(..))")
    public void createYing(JoinPoint joinPoint) throws Exception {


        // 1.  找出切点的参数,
        Object arg = (joinPoint.getArgs().length > 0 ? joinPoint.getArgs()[0] : null);
        if (arg instanceof YingFayun) {
            YingFayun yingFayun = (YingFayun) arg;
            Logutil.create(om, orderMapper, logService, yingFayun, EntityType.yingFayunInsert);
        } else if (arg instanceof YingBail) {
            YingBail yingBail = (YingBail) arg;
            Logutil.create(om, orderMapper, logService,yingBail, EntityType.yingBailInsert);
        }
        return;

    }

    /**
     *
     */
    @After("execution(* com.yimei.hs.ying.service..*.update*(..))")
    public void updateYing(JoinPoint joinPoint) throws Exception {
        Object arg = (joinPoint.getArgs().length > 0 ? joinPoint.getArgs()[0] : null);
        if (arg instanceof YingFayun) {
            YingFayun yingFayun = (YingFayun) arg;
            Logutil.create(om, orderMapper, logService, yingFayun, EntityType.yingFayunUpdate);
        } else if (arg instanceof YingBail) {
            YingFayun yingFayun = (YingFayun) arg;
            Logutil.create(om, orderMapper, logService, yingFayun, EntityType.yingBailUpdate);
        }
        return;
    }

    /**
     *
     */
    @After("execution(* com.yimei.hs.ying.service..*.delete(..))")
    public void deleteYing(JoinPoint joinPoint) throws Exception {
        Object args = (joinPoint.getArgs().length > 0 ? joinPoint.getArgs() : null);
        Long orderId = (Long) joinPoint.getArgs()[0];
        Long id = (Long) joinPoint.getArgs()[1];

        String fileName = joinPoint.getSignature().getDeclaringTypeName();
        joinPoint.getTarget().getClass();
        if (fileName.equals(YingFayunService.class.getName())) {
            YingFayun yingFayun = yingFayunMapper.selectByPrimaryKey(id);
            Logutil.create(om, orderMapper, logService, yingFayun, EntityType.yingFayunDel);

        } else if (fileName.equals(YingBailService.class.getName())) {
            YingBail yingBail = yingBailMapper.selectByPrimaryKey(id);
            Logutil.create(om, orderMapper, logService, yingBail, EntityType.yingFayunDel);
        }

    }
}
