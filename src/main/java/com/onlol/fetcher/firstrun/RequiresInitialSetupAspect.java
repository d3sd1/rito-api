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

    private boolean messageShown = true;

    @Around("@annotation(RequiresInitialSetup)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        if (this.runLogRepository.findAll().isEmpty() && !messageShown) {
            this.logger.debug("Prevented task for being executed before the initial setup is finished.");
            messageShown = true;
            return null;
        }
        messageShown = false;
        return joinPoint.proceed();
    }
}
