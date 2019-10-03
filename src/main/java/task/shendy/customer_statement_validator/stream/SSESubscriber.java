package task.shendy.customer_statement_validator.stream;

import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

public class SSESubscriber {

    //MARK: Properties

    private String name;
    private Flux<ServerSentEvent<String>> flux;
    private IServiceEventEmitter emitter;

    //MARK: Constructors

    SSESubscriber(String name) {
        this.name = name;
        flux = Flux.create(sink -> emitter = sink::next);
    }

    //MARK: Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Flux<ServerSentEvent<String>> getFlux() {
        return flux;
    }

    public void setFlux(Flux<ServerSentEvent<String>> flux) {
        this.flux = flux;
    }

    public IServiceEventEmitter getEmitter() {
        return emitter;
    }

    public void setEmitter(IServiceEventEmitter emitter) {
        this.emitter = emitter;
    }
}
