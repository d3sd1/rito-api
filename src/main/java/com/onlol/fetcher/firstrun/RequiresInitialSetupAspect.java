package com.onlol.fetcher.firstrun;

import com.onlol.fetcher.logger.LogService;
import com.onlol.fetcher.repository.RunLogRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RequiresInitialSetupAspect {
    @Autowired
    private LogService logger;

    @Autowired
    private RunLogRepository runLogRepository;

    @Around("@annotation(RequiresInitialSetup)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        if(this.runLogRepository.findAll().isEmpty()) {
            this.logger.debug("Prevented task for being executed before the initial setup is finished.");
            return null;
        }
        return joinPoint.proceed();
    }
}
