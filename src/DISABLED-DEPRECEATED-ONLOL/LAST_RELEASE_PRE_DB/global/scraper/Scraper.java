/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package global.scraper;

import global.env.DisabledEnv;
import global.setup.RequiresInitialSetup;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.AliasFor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Async
@RequiresInitialSetup
@Scheduled
@EventListener
@DisabledEnv
public @interface Scraper {
    @AliasFor(
            annotation = Async.class,
            attribute = "value",
            value = ""
    )
    String async() default "";
    @AliasFor(
            annotation = RequiresInitialSetup.class,
            attribute = "value"
    )
    boolean requiresInitialSetup() default true;
    @AliasFor(
            annotation = Scheduled.class,
            attribute = "fixedRate"
    )
    long scheduled() default 2000;
    @AliasFor(
            annotation = DisabledEnv.class,
            attribute = "value"
    )
    String[] disabledEnvs() default {};
    @AliasFor(
            annotation = EventListener.class,
            attribute = "value"
    )
    Class<?>[] executeOnEvents() default ApplicationReadyEvent.class;
}