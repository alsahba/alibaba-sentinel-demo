package com.sentinel;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.IntStream;

@RestController
@RequestMapping(value = "/")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping
    public String testIt() throws InterruptedException {
        IntStream.range(0, 400).mapToObj(this::isOdd).forEach(CompletableFuture::supplyAsync);

        TimeUnit.SECONDS.sleep(3);

        IntStream.range(400, 800).mapToObj(this::isOdd).forEach(CompletableFuture::supplyAsync);

        return "over or is it?";
    }

    private Supplier<String> isOdd(int number) {
        return () -> String.valueOf(testService.isOdd(number));
    }
}
