package task.shendy.customer_statement_validator.factory;

import org.springframework.http.ResponseEntity;
import task.shendy.customer_statement_validator.entity.HTTPResponse;
import task.shendy.customer_statement_validator.entity.Metadata;

import static org.springframework.http.HttpStatus.OK;

public class ResponseEntityFactory {

    public static ResponseEntity<HTTPResponse> ok() {
        return ResponseEntity.ok(HTTPResponseFactory.ok());
    }

    public static ResponseEntity<HTTPResponse> metadataFileFound(Metadata metadata) {
        return new ResponseEntity<>(HTTPResponseFactory.metadataFileFound(metadata), OK);
    }

    public static ResponseEntity<HTTPResponse> fileDeleted() {
        return ResponseEntity.ok(HTTPResponseFactory.fileDeleted());
    }

    public static ResponseEntity<HTTPResponse> fileNotFound() {
        return new ResponseEntity<>(HTTPResponseFactory.fileNotFound(), OK);
    }
}
