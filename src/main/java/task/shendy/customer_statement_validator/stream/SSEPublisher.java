package task.shendy.customer_statement_validator.stream;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import task.shendy.customer_statement_validator.entity.BriefMetadata;
import task.shendy.customer_statement_validator.factory.SSEFactory;

import java.util.HashMap;
import java.util.Map;

@Component
public class SSEPublisher {

    //MARK: Properties

    private Map<String, SSESubscriber> subscriberMap = new HashMap<>();

    //MARK: Public Methods

    public Flux<ServerSentEvent<String>> getPublisher(String subscriberName) {
        if (!subscriberMap.containsKey(subscriberName)) {
            subscriberMap.put(subscriberName, new SSESubscriber(subscriberName));
        }

        return subscriberMap.get(subscriberName).getFlux();
    }

    public void emitUploadedEvent(BriefMetadata briefMetadata) {
        subscriberMap.values().forEach(subscriber -> subscriber.getEmitter().next(SSEFactory.uploaded(briefMetadata)));
    }

    public void emitDeletedEvent(String eventId) {
        subscriberMap.values().forEach(subscriber -> subscriber.getEmitter().next(SSEFactory.deleted(eventId)));
    }

    public void emitValidatingEvent(String eventId) {
        subscriberMap.values().forEach(subscriber -> subscriber.getEmitter().next(SSEFactory.validating(eventId)));
    }

    public void emitValidatedEvent(BriefMetadata briefMetadata) {
        subscriberMap.values().forEach(subscriber -> subscriber.getEmitter().next(SSEFactory.validated(briefMetadata)));
    }
}
