package task.shendy.customer_statement_validator.entity;

import org.springframework.http.HttpStatus;

public class HTTPResponse {

    //MARK: Properties

    private int status;
    private Object data;

    //MARK: Constructor

    public HTTPResponse() {}

    public HTTPResponse(HttpStatus httpStatus, Object data) {
        this.status = httpStatus.value();
        this.data = data;
    }

    //MARK: Getters and Setters

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
