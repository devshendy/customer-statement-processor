package task.shendy.customer_statement_validator.factory;

import task.shendy.customer_statement_validator.entity.HTTPResponse;
import task.shendy.customer_statement_validator.entity.Metadata;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

class HTTPResponseFactory {

    //MARK: Public Methods

    static HTTPResponse ok() {
        return new HTTPResponse(OK, "ok");
    }

    static HTTPResponse metadataFileFound(Metadata metadata) {
        return new HTTPResponse(OK, metadata);
    }

    static HTTPResponse fileDeleted() {
        return new HTTPResponse(OK, "File has been deleted successfully");
    }

    static HTTPResponse fileNotFound() {
        return new HTTPResponse(NOT_FOUND, "No such file.");
    }
}
