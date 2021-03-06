package com.yimei.hs.cang.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.hs.cang.entity.CangChuku;
import com.yimei.hs.cang.entity.CangRuku;
import com.yimei.hs.cang.mapper.CangChukuMapper;
import com.yimei.hs.cang.mapper.CangRukuMapper;
import com.yimei.hs.cang.service.CangChukuService;
import com.yimei.hs.cang.service.CangRukuService;
import com.yimei.hs.enums.EntityType;
import com.yimei.hs.same.mapper.OrderMapper;
import com.yimei.hs.same.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class CangLogAspect<T> {

    private static final Logger logger = LoggerFactory.getLogger(CangLogAspect.class);

    @Autowired
    LogService logService;

    @Autowired
    ObjectMapper om;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CangRukuMapper cangRukuMapper;
    @Autowired
    private CangChukuMapper cangChukuMapper;

    /**
     *
     */
    @After("execution(* com.yimei.hs.cang.service..*.create(..))")
    public void createYing(JoinPoint joinPoint) throws Exception {


        // 1.  找出切点的参数,
        Object arg = (joinPoint.getArgs().length > 0 ? joinPoint.getArgs()[0] : null);
        if (arg instanceof CangRuku) {
            CangRuku cangRuku = (CangRuku) arg;
            logService.createLog(cangRuku, EntityType.cangRuKuInsert);
        } else if (arg instanceof CangChuku) {
            CangChuku cangChuku = (CangChuku) arg;
            logService.createLog(cangChuku, EntityType.cangChuKuInsert);
        }


    }

    /**
     *
     */
    @After("execution(* com.yimei.hs.cang.service..*.update*(..))")
    public void updateYing(JoinPoint joinPoint) throws Exception {
        Object arg = (joinPoint.getArgs().length > 0 ? joinPoint.getArgs()[0] : null);
        if (arg instanceof CangRuku) {
            CangRuku cangRuku = (CangRuku) arg;
            logService.createLog(cangRuku, EntityType.cangRuKuUpdate);
        } else if (arg instanceof CangChuku) {
            CangChuku cangChuku = (CangChuku) arg;
            logService.createLog(cangChuku, EntityType.cangChuKuUpdate);
        }

    }

    /**
     *
     */
    @After("execution(* com.yimei.hs.cang.service..*.delete(..))")
    public void deleteYing(JoinPoint joinPoint) throws Exception {

        Long id = (Long) joinPoint.getArgs()[0];

        String fileName = joinPoint.getSignature().getDeclaringTypeName();

        if (fileName.equals(CangRukuService.class.getName())) {
            CangRuku cangRuku = cangRukuMapper.selectByPrimaryKeyDeleted(id);
            logService.createLog(cangRuku, EntityType.cangRuKuDel);

        } else if (fileName.equals(CangChukuService.class.getName())) {
            CangChuku cangChuku = cangChukuMapper.selectByPrimaryKeyDeleted(id);
            logService.createLog(cangChuku, EntityType.cangChuKuDel);
        }

    }
}
