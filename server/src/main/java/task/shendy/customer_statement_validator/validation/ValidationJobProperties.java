package task.shendy.customer_statement_validator.validation;

import org.springframework.stereotype.Component;
import task.shendy.customer_statement_validator.entity.FailedRecord;
import task.shendy.customer_statement_validator.enums.ValidationStatus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ValidationJobProperties {

    //MARK: Properties

    private final Set<String> uniqueReferenceList = new HashSet<>();

    private String storageFileName = "";
    private final List<FailedRecord> failedRecordList = new ArrayList<>();
    private ValidationStatus validationStatus = ValidationStatus.VALIDATING;
    private int errors = 0;
    private int processedRecords = 0;

    //MARK: Getters and Setters

    public ValidationStatus getValidationStatus() {
        return this.validationStatus;
    }

    public void setValidationStatus(ValidationStatus validationStatus) {
        this.validationStatus = validationStatus;
    }

    public String getStorageFileName() {
        return storageFileName;
    }

    public void setStorageFileName(String storageFileName) {
        this.storageFileName = storageFileName;
    }

    public List<FailedRecord> getFailedRecordList() {
        return failedRecordList;
    }

    public int getErrors() {
        return errors;
    }

    public int getProcessedRecords() {
        return processedRecords;
    }

    //MARK: Helper Methods

    public void incrementErrorsByOne() {
        this.errors += 1;
    }

    public void incrementProcessedRecordsByOne() {
        this.processedRecords += 1;
    }

    public void addAllToFailedRecordList(List<? extends FailedRecord> failedRecordList) {
        this.failedRecordList.addAll(failedRecordList);
    }

    public boolean addToUniqueReferenceList(String reference) {
        return this.uniqueReferenceList.add(reference);
    }

    public void reset() {
        this.storageFileName = "";
        this.uniqueReferenceList.clear();
        this.failedRecordList.clear();
        this.validationStatus = ValidationStatus.VALIDATING;
        this.errors = 0;
        this.processedRecords = 0;
    }
}
