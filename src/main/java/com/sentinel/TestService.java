package com.sentinel;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@Async(value = "customThreadPool")
public class TestService {

    //Thread-safe logging
    private final Logger logger = LogManager.getLogger(this.getClass());

    @SentinelResource(value = "testresource", fallback = "fallbackMethod", blockHandler = "blockHandler")
    public CompletableFuture<String> isOdd(int number) {
        sleepForTwoSeconds();
        throwErrorIfNumberIsEven(number);

        logger.info("number {} is tested", number);
        return CompletableFuture.completedFuture("number " + number + " is odd");
    }

    //signature must be same as the original method
    public String fallbackMethod(int number, Throwable t) {
        String causeOfErr;
        if (t instanceof UnsupportedOperationException) {
            causeOfErr = "even number";
        } else {
            causeOfErr = "unknown error";
        }
        logger.error("fallback for: {}", causeOfErr);
        return "fallback";
    }

    //signature must be same as the original method, catches all block exceptions like Flow, Degrade
    public String blockHandler(int number, BlockException e) {
        logger.error("block handler for rule of: {}, resource: {}",
                e.getRule().getClass().getSimpleName(), e.getRule().getResource());
        return "block handler";
    }

    private void throwErrorIfNumberIsEven(int number) {
        if (number % 2 == 0) {
            throw new UnsupportedOperationException();
        }
    }

    private void sleepForTwoSeconds() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            logger.error("this thread hates to sleep");
        }
    }
}
