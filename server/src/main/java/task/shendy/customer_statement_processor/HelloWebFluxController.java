package task.shendy.customer_statement_processor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/hello_webflux")
public class HelloWebFluxController {

    @GetMapping
    public Mono<String> helloWebFlux() {
        return Mono.just("Hello WebFlux")
                .delayElement(Duration.ofSeconds(5));
    }

}
