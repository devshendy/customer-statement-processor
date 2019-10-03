package task.shendy.customer_statement_validator.stream;

import org.springframework.http.codec.ServerSentEvent;

public interface IServiceEventEmitter {
    void next(ServerSentEvent<String> serviceEvent);
}
