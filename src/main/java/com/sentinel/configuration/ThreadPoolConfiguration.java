package com.sentinel.configuration;

import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration(value = "customThreadPool")
public class ThreadPoolConfiguration extends ThreadPoolExecutor {

    //default cached pool config, each thread has an 60 seconds idle lifetime after that they are gone
    public ThreadPoolConfiguration() {
        super(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue<>());
    }
}
