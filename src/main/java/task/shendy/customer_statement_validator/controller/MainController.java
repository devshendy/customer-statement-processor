package task.shendy.customer_statement_validator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import task.shendy.customer_statement_validator.entity.HTTPResponse;
import task.shendy.customer_statement_validator.factory.ResponseEntityFactory;
import task.shendy.customer_statement_validator.stream.SSEPublisher;
import task.shendy.customer_statement_validator.util.LoggerUtil;

@RestController
@RequestMapping("/api")
public class MainController {

    //MARK: Properties

    private final SSEPublisher ssePublisher;

    @Autowired
    public MainController(SSEPublisher ssePublisher) {
        this.ssePublisher = ssePublisher;
    }

    //MARK: HTTP Handler Methods

    @CrossOrigin
    @GetMapping(value = "/ok")
    public ResponseEntity<HTTPResponse> ok() {
        return ResponseEntityFactory.ok();
    }

    @CrossOrigin
    @GetMapping(value = "/sse", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<ServerSentEvent<String>> sse() {
        return ssePublisher.getPublisher("sse")
                .doOnSubscribe(subscription -> LoggerUtil.info("SSE connection opened"));
    }
}
