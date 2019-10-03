package task.shendy.customer_statement_validator.enums;

import java.text.SimpleDateFormat;
import java.util.Date;

public enum DateFormat {
    UPLOAD_DATE("EEE MMM dd yyyy - K:mm:ss a"),
    ID("ddMMyyyyHHmmss");

    private final String dateFormat;

    DateFormat(String dateFormat) { this.dateFormat = dateFormat; }

    public String format(Date date) {
        return new SimpleDateFormat(this.dateFormat).format(date);
    }
}
