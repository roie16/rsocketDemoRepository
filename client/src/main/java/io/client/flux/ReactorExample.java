package io.client.flux;

import lombok.SneakyThrows;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.Executors.newWorkStealingPool;
import static reactor.core.scheduler.Schedulers.fromExecutorService;

/**
 * @author roieb
 * @package com.example.demo
 * @date 9/20/2020
 */
public class ReactorExample {

    @SneakyThrows
    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        Flux.just(1, 2, "A")
                .subscribeOn(fromExecutorService(newWorkStealingPool())) // Schedulers.parallel()
                .subscribe(elemt -> {
                    System.out.println("Thread id: " + Thread.currentThread().getId());
                    list.add(elemt);
                });
        System.out.println("Thread id: " + Thread.currentThread().getId());
        Thread.sleep(150);
        list.forEach(System.out::println);

    }


/**
 * zip
 Flux<Integer> integerFlux = Flux.just(1, 2, 3, 4, 5);
 Flux<String> stringFlux = Flux.just("A", "B", "C", "D");
 Flux.zip(integerFlux, stringFlux, (BiFunction<Integer, String, Pair<Integer, String>>) Pair::of).subscribe(System.out::println);
 */


/**
 * map vs flat map
 Flux<String> elements =
 Flux.just(1, 2, 3, 4)
 .map(integer -> integer * 2)
 .flatMap(s -> Mono.just(s.toString() + "1"));
 elements.subscribe(System.out::println);
 */


/**
 * groupBy
 Flux<Pair<Boolean, List<Integer>>> groupFlux = Flux.just(1, 2, 3, 4)
 .groupBy(integer -> integer % 2 == 0)
 .flatMap(booleanIntegerGroupedFlux -> booleanIntegerGroupedFlux.collectList()
 .map(groupedFluxes -> Pair.of(booleanIntegerGroupedFlux.key(), groupedFluxes)));

 groupFlux.subscribe(booleanListPair -> print(booleanListPair));

 private static void print(Pair<Boolean, List<Integer>> booleanListPair) {
 System.out.println(booleanListPair.getLeft() + " " + booleanListPair.getRight());
 }

 */

/**
 * parallel
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
 side effect
 List<Object> elements = new ArrayList<>();

 Flux.just(1, 2, 3, 4)
 .map(i -> i * 4)
 .doOnNext(elements::add)
 .collectList() // this is mono of list
 .doOnSuccess(list -> System.out.println("After: " + list.size()))
 .subscribe();
 elements.forEach(System.out::println);


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
 * with back pressure

 List<Integer> elements = new ArrayList<>();
 Flux.just(1, 2, 3, 4)
 .log()
 .doOnNext(elements::add)
 .subscribe(new SubscriberWithBackPressure());
 elements.forEach(System.out::println);
 */
}
