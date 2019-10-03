package task.shendy.customer_statement_validator.validation;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task.shendy.customer_statement_validator.entity.BriefMetadata;
import task.shendy.customer_statement_validator.entity.Metadata;
import task.shendy.customer_statement_validator.enums.ValidationStatus;
import task.shendy.customer_statement_validator.factory.MetadataFactory;
import task.shendy.customer_statement_validator.service.StorageService;
import task.shendy.customer_statement_validator.stream.SSEPublisher;

@Component
public class ValidationJobListener extends JobExecutionListenerSupport {

    //MARK: Properties

    private final StorageService storageService;
    private final ValidationJobProperties validationJobProperties;
    private final SSEPublisher ssePublisher;

    //MARK: Constructors

    @Autowired
    public ValidationJobListener(StorageService storageService, ValidationJobProperties validationJobProperties,
                                 SSEPublisher ssePublisher) {
        this.storageService = storageService;
        this.validationJobProperties = validationJobProperties;
        this.ssePublisher = ssePublisher;
    }

    //MARK: Overridden Methods

    @Override
    public void beforeJob(JobExecution jobExecution) {
        validationJobProperties.setValidationStatus(ValidationStatus.VALIDATING);
        validationJobProperties.setStorageFileName(jobExecution.getJobParameters().getString("storageFileName"));

        Metadata metadata = MetadataFactory.createFromBatchProcessProperties(validationJobProperties);
        storageService.updateMetadataFile(metadata);

        ssePublisher.emitValidatingEvent(metadata.getId());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            validationJobProperties.setValidationStatus(validationHasErrors() ? ValidationStatus.INVALID : ValidationStatus.VALID);

            Metadata metadata = MetadataFactory.createFromBatchProcessProperties(validationJobProperties);
            storageService.updateMetadataFile(metadata);

            BriefMetadata briefMetadata = MetadataFactory.createBrief(metadata);
            ssePublisher.emitValidatedEvent(briefMetadata);
        }

        validationJobProperties.reset();
    }

    //MARK: Private Methods

    private boolean validationHasErrors() {
        return validationJobProperties.getErrors() > 0;
    }

}
