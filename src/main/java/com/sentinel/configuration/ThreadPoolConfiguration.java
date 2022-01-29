package com.sentinel.configuration;

import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration(value = "customThreadPool")
public class ThreadPoolConfiguration extends ThreadPoolExecutor {

    //fixed thread pool configuration with the maximum number of threads is 40 and the queue capacity is 2000
    public ThreadPoolConfiguration() {
        super(40, 40, 60,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(2000));
    }
}
