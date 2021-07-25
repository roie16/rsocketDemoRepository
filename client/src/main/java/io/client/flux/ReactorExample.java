package io.client.flux;

import lombok.SneakyThrows;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

/**
 * @author roieb
 * @package com.example.demo
 * @date 9/20/2020
 */
public class ReactorExample {

    @SneakyThrows
    public static void main(String[] args) {

    }


/**
 * parallel
 *  Flux.just(1, 2, "A")
 *  .subscribeOn(fromExecutorService(newWorkStealingPool())) // Schedulers.parallel()
 *  .subscribe(elemt -> {
 *  System.out.println("Thread id: " + Thread.currentThread().getId() );
 *  elements.add(elemt);
 *  });
 *  System.out.println("Thread id: " + Thread.currentThread().getId());
 *  Thread.sleep(150);
 *  elements.forEach(System.out::println);
 */


/**
 * side effect
 *         List<Object> elements = new ArrayList<>();
 *
 *         Flux.just(1, 2, 3, 4)
 *                 .map(i -> i * 4)
 *                 .doOnNext(elements::add)
 *                 .collectList() // this is mono of list
 *                 .doOnSuccess(list -> System.out.println("After: " + list.size()))
 *                 .subscribe();
 *         elements.forEach(System.out::println);
 */

/**
 * Error handling
 *         Flux.just(1, 2, 3, 4)
 *                 .log()
 *                 .map(integer -> {
 *                     int res = integer * 2;
 *                     if (res == 6) {
 *                         throw new RuntimeException("this is wrong");
 *                     }
 *                     return res;
 *                 })
 *                 //.onErrorContinue((throwable, o) -> System.out.println("for " + o.toString() + " error " + throwable.getMessage()))
 *                 //.onErrorReturn(5)
 *                 //.onErrorResume(throwable -> Mono.just(5))
 *                 .subscribe(System.out::println);
 */


/**
 * with back pressure
 *
 *  List<Integer> elements = new ArrayList<>();
 *  Flux.just(1, 2, 3, 4)
 *  .log()
 *  .doOnNext(elements::add)
 *  .subscribe(new SubscriberWithBackPressure());
 *  elements.forEach(System.out::println);
 */
}
