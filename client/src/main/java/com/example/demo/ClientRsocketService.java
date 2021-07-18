package com.example.demo;

import com.example.demo.data.HelloRequest;
import com.example.demo.data.HelloResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author roieb
 * @package io.demo.demo
 * @date 9/18/2020
 */
@Slf4j
@Service
public class ClientRsocketService {

    private final RSocketRequester rsocketRequester;

    public ClientRsocketService(RSocketRequester.Builder rsocketRequesterBuilder) {
        this.rsocketRequester = rsocketRequesterBuilder.tcp("localhost", 8888);
    }

    public Flux<HelloResponse> fluxCall(HelloRequest helloRequest) {
        log.info(helloRequest.toString());
        return rsocketRequester.route("say.hello.reactive.flux").data(helloRequest)
                .retrieveFlux(HelloResponse.class);
    }

    public Mono<HelloResponse> monoCall(HelloRequest helloRequest) {
        log.info(helloRequest.toString());
        return rsocketRequester.route("say.hello.reactive.mono").data(helloRequest)
                .retrieveMono(HelloResponse.class).onErrorReturn(new HelloResponse("Hello You are probably illegal please check info"));
    }

    public Flux<HelloResponse> fluxWithError(Flux<HelloRequest> helloRequestFlux) {
        return Flux.mergeSequential(helloRequestFlux.map(this::monoCall));
    }

    public Flux<HelloResponse> fluxWithBackPressure(Flux<HelloRequest> helloRequestFlux) {
        return rsocketRequester.route("flux.with.backpressure").data(helloRequestFlux)
                .retrieveFlux(HelloResponse.class);
    }

}




























//where to put this??
//.onErrorReturn(new HelloResponse("Hello You are probably illegal please check info "))