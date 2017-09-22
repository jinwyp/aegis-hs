package com.yimei.hs.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hary on 2017/9/22.
 */

@Aspect
@Configuration
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(* com.yimei.hs.service..*.*(..))")
    public void service() {
    }

    @Pointcut("execution(* com.yimei.hs.controller..*.*(..))")
    public void controller() {
    }

//    @Around("service()")
//    public Object doAroundService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        return doLog(proceedingJoinPoint, "service");
//    }

    @Around("controller()")
    public Object doAroundController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return doLog(proceedingJoinPoint, "controller");
    }

    private Object doLog(ProceedingJoinPoint proceedingJoinPoint, String name) throws Throwable {

//        Object othis = proceedingJoinPoint.getThis();
//        Logger ologger = (Logger)othis.getClass().getField("logger").get(othis);
//        logger.info("原始logger");

        Signature sigature = proceedingJoinPoint.getSignature();
        logger.info("{} {} called with following args", name, sigature.toShortString());
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            logger.info("{}", arg);
        }
        Object o = proceedingJoinPoint.proceed();
        logger.info("{} {} return with {}", name, sigature.toShortString(), o);
        return o;
    }

}
