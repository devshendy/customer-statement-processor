package task.shendy.customer_statement_validator.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoggerUtil {

    //MARK: Static Properties

    private static final Logger logger = LoggerFactory.getLogger(LoggerUtil.class);

    //MARK: Public Static Methods

    public static void info(String msg) {
        logger.info("app-log : {}", msg);
    }

    public static void error(String msg) {
        logger.error("app-log : {}", msg);
    }
}
