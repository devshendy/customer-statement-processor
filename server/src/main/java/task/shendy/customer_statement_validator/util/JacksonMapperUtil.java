package task.shendy.customer_statement_validator.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import task.shendy.customer_statement_validator.entity.BriefMetadata;
import task.shendy.customer_statement_validator.entity.Metadata;

import java.io.File;
import java.io.IOException;

public class JacksonMapperUtil {

    //MARK: Properties

    private static final ObjectMapper jacksonObjectMapper = new ObjectMapper();

    //MARK: Static Initialization

    static {
        jacksonObjectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    //MARK: Public Methods

    public static String writeBriefMetadataAsString(BriefMetadata briefMetadata) {
        try {
            return jacksonObjectMapper.writeValueAsString(briefMetadata);

        } catch (IOException e) { // TODO: Find a proper way to handle it
            e.printStackTrace();
            return null;
        }
    }

    public static void writeMetadataToFile(Metadata metadata, File metadataFile) {
        try {
            jacksonObjectMapper.writeValue(metadataFile, metadata);

        } catch (IOException e) { // TODO: Find a proper way to handle it
            e.printStackTrace();
        }
    }

    public static Metadata mapFileIntoMetadata(File metadataFile) {
        try {
            return jacksonObjectMapper.readValue(metadataFile, Metadata.class);

        } catch (IOException e) { // TODO: Find a proper way to handle it
            e.printStackTrace();
            return null;
        }
    }

    public static BriefMetadata mapFileIntoBriefMetadata(File metadataFile) {
        try {
            return jacksonObjectMapper.readValue(metadataFile, BriefMetadata.class);

        } catch (IOException e) { // TODO: Find ap proper way to handle it
            e.printStackTrace();
            return null;
        }
    }
}
