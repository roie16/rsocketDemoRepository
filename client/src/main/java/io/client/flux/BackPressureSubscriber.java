package io.client.flux;

import io.client.data.HelloResponse;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * @author roieb
 * @package com.example.demo
 * @date 9/27/2020
 */
@Slf4j
public class BackPressureSubscriber implements Subscriber<HelloResponse> {

    private static final Integer NUMBER_OF_REQUESTED_ITEMS = 5;
    private Subscription subscription;
    int receivedItems;

    @Override
    public void onSubscribe(Subscription s) {
        this.subscription = s;
        subscription.request(NUMBER_OF_REQUESTED_ITEMS);
    }

    @Override
    public void onNext(HelloResponse helloResponse) {
        receivedItems++;
        if (receivedItems % NUMBER_OF_REQUESTED_ITEMS == 0) {
            log.info("Requesting next [{}] elements", NUMBER_OF_REQUESTED_ITEMS);
            subscription.request(NUMBER_OF_REQUESTED_ITEMS);
        }
    }

    @Override
    public void onError(Throwable t) {
        log.error("Stream subscription error: ", t);
    }

    @Override
    public void onComplete() {
        log.info("Completing subscription");
    }
}