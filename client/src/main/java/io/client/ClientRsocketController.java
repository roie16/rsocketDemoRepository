package io.client;

import io.client.data.HelloRequest;
import io.client.data.HelloResponse;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author roieb
 * @package com.example.demo
 * @date 9/18/2020
 */
@RestController
@AllArgsConstructor
@Value
@Slf4j
public class ClientRsocketController {

    ClientRsocketService clientRsocketService;

    @GetMapping(value = "/hellostreamflux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Publisher<HelloResponse> helloStreamFlux(@RequestParam String name, @RequestParam int age) {
        HelloRequest helloRequest = new HelloRequest(name, age);
        return clientRsocketService.singlePayloadCall(helloRequest);
    }


    @GetMapping(value = "/hellomono")
    public Publisher<HelloResponse> helloMono(@RequestParam String name, @RequestParam int age) {
        HelloRequest helloRequest = new HelloRequest(name, age);
        return clientRsocketService.monoCall(helloRequest);
    }

    @GetMapping(value = "/fluxwitherrormono", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Publisher<HelloResponse> fluxWithErrorMono() {
        Flux<HelloRequest> flux = Flux.fromStream(Stream.of(new HelloRequest("Roie", 34), new HelloRequest("Daniel", 17),
                new HelloRequest("Nitzan", 41)));
        return clientRsocketService.fluxWithError(flux);
    }

    @GetMapping(value = "/fluxwitherror")
    public Publisher<HelloResponse> fluxWithError() {
        Flux<HelloRequest> flux = Flux.fromStream(Stream.of(new HelloRequest("Roie", 34), new HelloRequest("Roni", 27), new HelloRequest("Daniel", 17),
                new HelloRequest("Nitzan", 41)));
        return clientRsocketService.fluxCall(flux);
    }

    @GetMapping(value = "/fluxwithbackpressure", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Publisher<HelloResponse> fluxWithBackpressure() {
        return clientRsocketService.fluxWithBackPressure(generate100HelloRequest());
    }

    private Flux<HelloRequest> generate100HelloRequest() {
        return Flux.
                fromStream(IntStream.range(20, 121).mapToObj(age ->
                        new HelloRequest(RandomStringUtils.randomAlphanumeric(5), age)))
                .log();
    }
}
