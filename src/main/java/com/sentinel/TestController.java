package com.sentinel;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.IntStream;

@RestController
@RequestMapping(value = "/")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping
    public String testIt() throws InterruptedException {
        IntStream.range(0, 25).mapToObj(this::isOdd).forEach(CompletableFuture::supplyAsync);

        Thread.sleep(3000);

        IntStream.range(25, 50).mapToObj(this::isOdd).forEach(CompletableFuture::supplyAsync);

        return "over or is it?";
    }

    private Supplier<String> isOdd(int number) {
        return () -> testService.isOdd(number);
    }
}
