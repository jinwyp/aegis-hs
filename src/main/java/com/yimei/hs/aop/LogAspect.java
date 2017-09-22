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
    public void controller() {
    }

    @Around("controller()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Signature sigature = proceedingJoinPoint.getSignature();


        logger.info("service {} called with following args", sigature.toShortString());
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            logger.info("{}", arg);
        }
        Object o = proceedingJoinPoint.proceed();
        logger.info("service {} return with {}", sigature.toShortString(), o);
        return o;
    }
}
