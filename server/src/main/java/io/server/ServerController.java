package io.server;

import io.server.data.HelloRequest;
import io.server.data.HelloResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.Duration;
import java.util.stream.Stream;

/**
 * @author roieb
 * @package io.demo.demo
 * @date 9/4/2020
 */

@RestController("/")
@Validated
public class ServerController {

    @GetMapping("sayhello")
    public ResponseEntity<HelloResponse> sayHello(@RequestParam @NotBlank String name, @RequestParam @NonNull @Min(29) int age) {
        return ResponseEntity.ok(generateHelloMsg(name, age));
    }

    @MessageMapping("say.hello.reactive.flux")
    Flux<HelloResponse> helloResponseFlux(@Payload @Valid HelloRequest request) {
        Stream<HelloResponse> helloResponseStream = Stream.generate(() -> generateHelloMsg(request.name(), request.age()));
        return Flux.fromStream(helloResponseStream).delayElements(Duration.ofSeconds(1)).take(20);
    }

    @MessageMapping("say.hello.reactive.mono")
    Mono<HelloResponse> helloResponseMono(@Payload @Valid HelloRequest request) {
        return Mono.just(generateHelloMsg(request.name(), request.age()));
    }

    @MessageMapping("flux.with.backpressure")
    Flux<HelloResponse> fluxWithBackpressure(@Payload Flux<HelloRequest> request) {
        return request.delayElements(Duration.ofSeconds(2)).map(helloRequest -> generateHelloMsg(helloRequest.name(), helloRequest.age()));
    }

    private HelloResponse generateHelloMsg(String name, int age) {
        return switch (name) {
            case "Roie" -> new HelloResponse("Hello " + name + " you are old");
            case "Roni" -> {
                yield new HelloResponse("Hello " + name + " thank you for making this lecture happen :) ");
            }
            default -> new HelloResponse("Hello " + name + " you are young at hart but your age is: " + age);
        };
    }
}
