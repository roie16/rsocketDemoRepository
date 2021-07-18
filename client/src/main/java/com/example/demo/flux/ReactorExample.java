package com.example.demo.flux;

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
        List<Integer> elements = new ArrayList<>();

        Flux.just(1, 2, 3, 4)
                .log()
                .subscribe();

        Flux.just(1, 2, 3, 4)
                .log()
                .map(integer -> integer * 2)
                .subscribe(new SubscriberWithBackPressure(elements));
        elements.forEach(System.out::println);

        //parallel
        Flux.just(1, 2, 3, 4)
                .log()
                .map(i -> i * 3)
                .subscribeOn(Schedulers.parallel())
                .subscribe(t -> {
                    System.out.println(t + " thread id: " + Thread.currentThread().getId());
                    elements.add(t);
                });
        System.out.println("size of arrList(before the wait): " + elements.size());
        System.out.println("Thread id: " + Thread.currentThread().getId() + ": id of main thread ");
        Thread.sleep(100);
        System.out.println("size of arrList(after the wait): " + elements.size());

        //parallel with side effect
        Flux.just(1, 2, 3, 4)
                .log()
                .map(i -> i * 4)
                .subscribeOn(Schedulers.parallel())
                .doOnNext(elements::add)
                .doOnComplete(() -> System.out.println("After: " + elements.size()))
                .blockLast(); // subscribe() --> also a good option

        //parallel with side effect
        Flux.just(1, 2, 3, 4)
                .log()
                .map(i -> i * 4)
                .subscribeOn(Schedulers.parallel())
                .doOnNext(elements::add)
                .collectList() // this is mono of list
                .doOnSuccess(list -> System.out.println("After: " + list.size()))
                .block(); // need to block,  should use subscribe()
    }
}
