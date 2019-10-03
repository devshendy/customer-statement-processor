package task.shendy.customer_statement_validator.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "file_name", "upload_date", "validation" })
public class BriefMetadata {

    //MARK: Properties

    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "file_name")
    private String fileName;

    @JsonProperty(value = "upload_date")
    private String uploadDate;

    @JsonProperty(value = "validation")
    private BriefValidationResult briefValidationResult;

    //MARK: Constructors

    public BriefMetadata() {}

    public BriefMetadata(String id, String fileName, String uploadDate) {
        this.id = id;
        this.fileName = fileName;
        this.uploadDate = uploadDate;
        this.briefValidationResult = new BriefValidationResult();
    }

    //MARK: Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public BriefValidationResult getBriefValidationResult() {
        return briefValidationResult;
    }

    public void setBriefValidationResult(BriefValidationResult briefValidationResult) {
        this.briefValidationResult = briefValidationResult;
    }

    //MARK: Overridden Methods

    @Override
    public String toString() {
        return "customer_statement_short_metadata: {" +
                "\t id: '" + this.id + "'," +
                "\t file_name: '" + this.fileName + "'," +
                "\t upload_date: '" + this.uploadDate + "'," +
                "\t validation: " + this.briefValidationResult +
                "}";
    }
}
