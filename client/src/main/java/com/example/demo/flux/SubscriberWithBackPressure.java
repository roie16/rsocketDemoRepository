package com.example.demo.flux;

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
    private final Collection<Integer> elements;

    public SubscriberWithBackPressure(Collection<Integer> elements) {
        this.elements = elements;
    }

    @Override
    public void onSubscribe(Subscription s) {
        this.s = s;
        s.request(2);
    }

    @Override
    public void onNext(Integer integer) {
        elements.add(integer);
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
