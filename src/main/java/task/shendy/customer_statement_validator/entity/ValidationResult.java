package task.shendy.customer_statement_validator.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import task.shendy.customer_statement_validator.enums.ValidationStatus;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({ "status", "summary", "failed_records" })
public class ValidationResult {

    //MARK: Properties

    @JsonProperty(value = "status")
    private ValidationStatus status;

    @JsonProperty(value = "summary")
    private ValidationSummary summary;

    @JsonProperty(value = "failed_records")
    private List<FailedRecord> failedRecordList;

    //MARK: Constructors

    public ValidationResult() {
        this.status = ValidationStatus.PENDING;
        this.summary = new ValidationSummary();
        this.failedRecordList = new ArrayList<>();
    }

    public ValidationResult(ValidationStatus status, ValidationSummary summary, List<FailedRecord> failedRecordList) {
        this.status = status;
        this.summary = summary;
        this.failedRecordList = failedRecordList;
    }

    //MARK: Getters and Setters

    public ValidationStatus getStatus() {
        return status;
    }

    public void setStatus(ValidationStatus status) {
        this.status = status;
    }

    public ValidationSummary getSummary() {
        return summary;
    }

    public void setSummary(ValidationSummary summary) {
        this.summary = summary;
    }

    public List<FailedRecord> getFailedRecordList() {
        return failedRecordList;
    }

    public void setFailedRecordList(List<FailedRecord> failedRecordList) {
        this.failedRecordList = failedRecordList;
    }

    //MARK: Overridden Methods

    @Override
    public String toString() {
        return "{" +
                "\t status: '" + status + "'," +
                "\t summary: " + summary + "," +
                "\t failed_record_list: " + failedRecordList +
                "}";
    }
}
