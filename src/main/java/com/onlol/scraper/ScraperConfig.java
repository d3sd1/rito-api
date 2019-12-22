/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.onlol.scraper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@ComponentScan
public class ScraperConfig {
    @Value("${onriot.thread.core.pool.size}")
    private short corePoolSize;
    @Value("${onriot.thread.max.pool.size}")
    private short maxPoolSize;

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(this.corePoolSize);
        pool.setMaxPoolSize(this.maxPoolSize);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }

}