package task.shendy.customer_statement_validator.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "errors", "records" })
public class ValidationSummary {

    //MARK: Properties

    @JsonProperty(value = "errors")
    private int errors;

    @JsonProperty(value = "total_records")
    private int totalRecords;

    //MARK: Constructors

    public ValidationSummary() {
        this.errors = 0;
        this.totalRecords = 0;
    }

    public ValidationSummary(int errors, int totalRecords) {
        this.errors = errors;
        this.totalRecords = totalRecords;
    }

    //MARK: Getters and Setters

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    //MARK: Overridden Methods

    @Override
    public String toString() {
        return "{" +
                "\t errors: " + errors + "," +
                "\t records: " + totalRecords +
                '}';
    }
}
