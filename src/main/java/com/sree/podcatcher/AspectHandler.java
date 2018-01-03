/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sree.podcatcher;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 *
 * @author sreejithu.panicker
 *
 */
@Component
@Aspect
public class AspectHandler {
    Logger logger = Logger.getLogger("app");
    @AfterThrowing(pointcut = "execution(* com.sree.podcatcher.*.*(..))", throwing = "error")
    public void ExceptionCatcher(JoinPoint p, Throwable error) {
    }
    @Before("execution(* com.sree.podcatcher.service.WebPodcast.*(..))")
    public void allMethodsPointcutBefore(JoinPoint joinPoint) {
        logger.debug(joinPoint.getSignature().getName()+ " , " + "Started ");
    }
    @After("execution(* com.sree.podcatcher.service.WebPodcast.*(..))")
    public void allMethodsPointcutAfter(JoinPoint joinPoint) {
        logger.debug(joinPoint.getSignature().getName()+ " , " + "Ended ");
    }

}
