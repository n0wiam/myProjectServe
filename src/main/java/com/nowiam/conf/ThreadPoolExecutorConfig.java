package com.nowiam.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class ThreadPoolExecutorConfig {
    private static final int CORE=3;
    private static final int MAX_CORE=5;
    private static final long KEEP_ALIVE_TIME=30;
    @Bean
    public ThreadPoolExecutor threadPoolExecutor(){
        return new ThreadPoolExecutor(CORE, MAX_CORE, KEEP_ALIVE_TIME, TimeUnit.SECONDS,new SynchronousQueue<>());
    }
}
