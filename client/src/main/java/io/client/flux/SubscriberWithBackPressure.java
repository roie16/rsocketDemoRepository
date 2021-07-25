package io.client.flux;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Collection;

/**
 * @author roieb
 * @package com.example.demo
 * @date 9/20/2020
 */
public class SubscriberWithBackPressure implements Subscriber<Integer> {

    private Subscription s;
    private int onNextAmount;

    @Override
    public void onSubscribe(Subscription s) {
        this.s = s;
        s.request(2);
    }

    @Override
    public void onNext(Integer integer) {
        onNextAmount++;
        if (onNextAmount % 2 == 0) {
            s.request(2);
        }
    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onComplete() {

    }
}
