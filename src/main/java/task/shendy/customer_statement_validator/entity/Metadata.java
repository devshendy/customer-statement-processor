package task.shendy.customer_statement_validator.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.io.FilenameUtils;
import task.shendy.customer_statement_validator.enums.DateFormat;

import java.util.Date;


@JsonPropertyOrder({ "id", "file_name", "storage_file_name", "upload_date", "validation" })
public class Metadata {

    //MARK: Properties

    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "file_name")
    private String fileName;

    @JsonProperty(value = "storage_file_name")
    private String storageFileName;

    @JsonProperty(value = "upload_date")
    private String uploadDate;

    @JsonProperty(value = "validation")
    private ValidationResult validationResult;

    //MARK: Constructors

    public Metadata() {}

    public Metadata(String fileName) {
        Date now = new Date();
        this.id = DateFormat.ID.format(now);
        this.storageFileName = this.id + "." + FilenameUtils.getExtension(fileName);
        this.uploadDate = DateFormat.UPLOAD_DATE.format(now);

        this.fileName = fileName;
        this.validationResult = new ValidationResult();
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

    public String getStorageFileName() {
        return storageFileName;
    }

    public void setStorageFileName(String storageFileName) {
        this.storageFileName = storageFileName;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public ValidationResult getValidationResult() {
        return validationResult;
    }

    public void setValidationResult(ValidationResult validationResult) {
        this.validationResult = validationResult;
    }

    //MARK: Overridden Methods

    @Override
    public String toString() {
        return "customer_statement_full_metadata: {" +
                "\t id: '" + this.id + "'," +
                "\t file_name: '" + this.fileName + "'," +
                "\t storage_file_name: '" + this.storageFileName + "'," +
                "\t upload_date: '" + this.uploadDate + "'," +
                "\t validation: " + this.validationResult +
                "}";
    }
}
