/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package global.scraper;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Initial setup label logic.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Aspect
@Component
public class ScraperAspect {

    @Before("@annotation(com.global.scraper.Scraper)")
    public void beforeScraper(JoinPoint joinPoint) {
    }

    @After("@annotation(com.global.scraper.Scraper)")
    public void afterScraper(JoinPoint joinPoint) {

    }
    @Around("@annotation(com.global.scraper.Scraper)")
    public void disabledEnv(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        joinPoint.proceed();
    }
}
