/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package global.setup;

import global.repository.RunLogRepository;
import global.services.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Initial setup label logic.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Aspect
@Component
public class RequiresInitialSetupAspect {
    private Logger logger;
    private RunLogRepository runLogRepository;

    public RequiresInitialSetupAspect(Logger logger, RunLogRepository runLogRepository) {
        this.logger = logger;
        this.runLogRepository = runLogRepository;
    }

    private boolean messageShown = true;

    /**
     * Log execution time object.
     *
     * @param joinPoint the join point
     * @return the object
     * @throws Throwable the throwable
     */
    @Around("@annotation(com.global.setup.RequiresInitialSetup)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        if (this.runLogRepository.findAll().isEmpty()) {
            if (!messageShown) {
                this.logger.debug("Prevented task for being executed before the initial setup is finished.");
                messageShown = true;
            }
            return null;
        }
        messageShown = false;
        return joinPoint.proceed();
    }
}
