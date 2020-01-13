/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package global.env;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
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
public class DisabledEnvAspect {
    @Value("${spring.profiles.active}")
    private String currentEnv;

    /**
     * Disabled env object. Disables method for set environment/s.
     *
     * @param joinPoint the join point
     * @return the object
     * @throws Throwable the throwable
     */
    @Around("@annotation(com.global.env.DisabledEnv)")
    public Object disabledEnv(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        DisabledEnv myAnnotation = method.getAnnotation(DisabledEnv.class);
        String[] disabledEnvs = myAnnotation.value();
        System.out.println("DISABLED ENVS: " + disabledEnvs);
        System.out.println("curr ENV: " + currentEnv);
        for(int i = 0; i < disabledEnvs.length; i++) {
            if(disabledEnvs[i].equalsIgnoreCase(currentEnv)) {
                System.out.println("Diisabling for current env.");
                return null;
            }
        }
        return joinPoint.proceed();
    }
}
