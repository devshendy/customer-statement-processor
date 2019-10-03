package task.shendy.customer_statement_validator.factory;

import org.springframework.http.codec.ServerSentEvent;
import task.shendy.customer_statement_validator.entity.BriefMetadata;
import task.shendy.customer_statement_validator.enums.ServiceEventType;
import task.shendy.customer_statement_validator.util.JacksonMapperUtil;

import java.util.Objects;

public class SSEFactory {

    //MARK: Public Methods

    public static ServerSentEvent<String> uploaded(BriefMetadata briefMetadata) {
        String briefMetadataAsString = JacksonMapperUtil.writeBriefMetadataAsString(briefMetadata);

        return ServerSentEvent.<String>builder()
                .id(briefMetadata.getId())
                .event(ServiceEventType.UPLOADED.name())
                .data(Objects.requireNonNull(briefMetadataAsString))
                .build();
    }

    public static ServerSentEvent<String> deleted(String eventId) {
        return ServerSentEvent.<String>builder()
                .id(eventId)
                .event(ServiceEventType.DELETED.name())
                .data("")
                .build();
    }

    public static ServerSentEvent<String> validating(String eventId) {
        return ServerSentEvent.<String>builder()
                .id(eventId)
                .event(ServiceEventType.VALIDATING.name())
                .data("")
                .build();
    }

    public static ServerSentEvent<String> validated(BriefMetadata briefMetadata) {
        String briefMetadataAsString = JacksonMapperUtil.writeBriefMetadataAsString(briefMetadata);

        return ServerSentEvent.<String>builder()
                .id(briefMetadata.getId())
                .event(ServiceEventType.VALIDATED.name())
                .data(Objects.requireNonNull(briefMetadataAsString))
                .build();
    }
}
