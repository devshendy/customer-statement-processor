package task.shendy.customer_statement_validator.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import task.shendy.customer_statement_validator.enums.ValidationError;

import java.util.List;

@JsonPropertyOrder({ "reference", "description", "errors" })
public class FailedRecord {

    //MARK: Properties

    @JsonProperty("reference")
    private String reference;

    @JsonProperty("description")
    private String description;

    @JsonProperty("errors")
    private List<ValidationError> errorsList;

    //MARK: Constructors

    public FailedRecord() {  }

    public FailedRecord(Record record, List<ValidationError> errorList) {
        this.reference = record.getReference();
        this.description = record.getDescription();
        this.errorsList = errorList;
    }

    //MARK: Getters and Setters

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ValidationError> getErrorsList() {
        return errorsList;
    }

    public void setErrorsList(List<ValidationError> errorsList) {
        this.errorsList = errorsList;
    }

    //MARK: Overridden Methods

    @Override
    public String toString() {
        return "{" +
                "\t reference: '" + reference + "'," +
                "\t description: '" + description + "'," +
                "\t errors: " + errorsList +
                "}";
    }
}
