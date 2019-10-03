package task.shendy.customer_statement_validator.factory;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.codec.multipart.FilePart;
import task.shendy.customer_statement_validator.entity.*;
import task.shendy.customer_statement_validator.enums.ValidationStatus;
import task.shendy.customer_statement_validator.util.JacksonMapperUtil;
import task.shendy.customer_statement_validator.validation.ValidationJobProperties;

import java.io.File;
import java.util.List;

import static task.shendy.customer_statement_validator.controller.StorageController.storagePath;

public class MetadataFactory {

    //MARK: Public Methods

    public static Metadata createFromFilePart(FilePart filePart) {
        return new Metadata(filePart.filename());
    }

    public static Metadata createFromBatchProcessProperties(ValidationJobProperties validationJobProperties) {
        String storageFileName = validationJobProperties.getStorageFileName();
        List<FailedRecord> failedRecordList = validationJobProperties.getFailedRecordList();
        ValidationStatus validationStatus = validationJobProperties.getValidationStatus();
        ValidationSummary validationSummary = new ValidationSummary(validationJobProperties.getErrors(), validationJobProperties.getProcessedRecords());
        ValidationResult validationResult = new ValidationResult(validationStatus, validationSummary, failedRecordList);

        File metadataFile = new File(storagePath + FilenameUtils.getBaseName(storageFileName) + ".json");

        Metadata metadata = JacksonMapperUtil.mapFileIntoMetadata(metadataFile);
        metadata.setValidationResult(validationResult);

        return metadata;
    }

    public static BriefMetadata createBrief(Metadata metadata) {
        BriefMetadata briefMetadata = new BriefMetadata(metadata.getId(), metadata.getFileName(), metadata.getUploadDate());

        BriefValidationResult briefValidationResult = new BriefValidationResult();
        briefValidationResult.setStatus(metadata.getValidationResult().getStatus());
        briefValidationResult.setSummary(metadata.getValidationResult().getSummary());

        briefMetadata.setBriefValidationResult(briefValidationResult);

        return briefMetadata;
    }
}
