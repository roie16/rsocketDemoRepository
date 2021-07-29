package io.client.flux;

import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import static java.util.concurrent.Executors.newWorkStealingPool;
import static reactor.core.scheduler.Schedulers.fromExecutorService;

/**
 * @author roieb
 * @package com.example.demo
 * @date 9/20/2020
 */
public class ReactorExamples {

    @SneakyThrows
    public static void main(String[] args) {
    }


/**
 * zip
 Flux<Integer> integerFlux = Flux.just(1, 2, 3, 4, 5);
 Flux<String> stringFlux = Flux.just("A", "B", "C", "D");
 Flux.zip(integerFlux, stringFlux, (BiFunction<Integer, String, Pair<Integer, String>>) Pair::of).subscribe(System.out::println);
 */

/**
 Error handling
 Flux.just(1, 2, 3, 4)
 .log()
 .map(integer -> {
 int res = integer * 2;
 if (res == 6) {
 throw new RuntimeException("this is wrong");
 }
 return res;
 })
 //.onErrorContinue((throwable, o) -> System.out.println("for " + o.toString() + " error " + throwable.getMessage()))
 //.onErrorReturn(5)
 //.onErrorResume(throwable -> Mono.just(5))
 .subscribe(System.out::println);
 */

/**
 * Schedulers(go over )
 Flux.just(1, 2, "A")
 .subscribeOn(fromExecutorService(newWorkStealingPool())) // Schedulers.parallel()
 .subscribe(elemt -> {
 System.out.println("Thread id: " + Thread.currentThread().getId() );
 elements.add(elemt);
 });
 System.out.println("Thread id: " + Thread.currentThread().getId());
 Thread.sleep(150);
 elements.forEach(System.out::println);
 */

/**
 * with costume backpressure
 List<Integer> elements = new ArrayList<>();
 Flux.just(1, 2, 3, 4)
 .log()
 .doOnNext(elements::add)
 .subscribe(new SubscriberWithBackPressure());
 elements.forEach(System.out::println);
 */

}
