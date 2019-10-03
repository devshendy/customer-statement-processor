package task.shendy.customer_statement_validator.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import task.shendy.customer_statement_validator.enums.ValidationStatus;

@JsonPropertyOrder({ "status", "summary" })
public class BriefValidationResult {

    //MARK: Properties

    @JsonProperty(value = "status")
    private ValidationStatus status;

    @JsonProperty(value = "summary")
    private ValidationSummary summary;

    //MARK: Constructors

    public BriefValidationResult() {
        this.status = ValidationStatus.PENDING;
        this.summary = new ValidationSummary();
    }

    public BriefValidationResult(ValidationStatus validationStatus, ValidationSummary validationSummary) {
        this.status = validationStatus;
        this.summary = validationSummary;
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

    //MARK: Overridden Methods

    @Override
    public String toString() {
        return "{" +
                "\t status: '" + status + "'," +
                "\t summary: " + summary +
                "}";
    }
}
