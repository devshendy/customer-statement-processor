package task.shendy.customer_statement_validator;

import org.apache.commons.io.FileUtils;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import task.shendy.customer_statement_validator.util.LoggerUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;

import static task.shendy.customer_statement_validator.controller.StorageController.storagePath;

/**
 * A simple service for validating Customer Statement docs of some bank.
 *
 * Some bank receives monthly deliveries of customer statement records.
 * his information is delivered in two formats, CSV and XML.
 * This project was build as a demo task to validate these documents.
 *
 * @author Ahmed Shendy
 */
@EnableAsync
@EnableBatchProcessing
@SpringBootApplication
public class CustomerStatementDocValidatorApplication {

    private final ConfigurableApplicationContext context;

     @Autowired
     CustomerStatementDocValidatorApplication(ConfigurableApplicationContext context) {
        this.context = context;
    }

    public static void main(String[] args) { SpringApplication.run(CustomerStatementDocValidatorApplication.class, args); }

    @Bean
    public Executor simpleAsyncTaskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

    @Bean
    CommandLineRunner init() {
        return args -> this.prepareStorageDirectory();
    }

    /**
     * Prepare storagePath (ex: /tmp/storage) directory for use.
     * It will will recreate this directory, and exit the application if it encounters any error.
     */
    private void prepareStorageDirectory() {
        LoggerUtil.info("Prepare app storage directory.");

        File storage = new File(storagePath);
        if (storage.exists()) {
            try {
                FileUtils.deleteDirectory(storage);

            } catch (IOException e) {
                logDirectoryCannotBeDeletedError();
            }
        }

        if (!storage.mkdir()) {
            logDirectoryCannotBeCreatedError();
        }
    }

    private void logDirectoryCannotBeDeletedError() {
        logErrorThenShutdown(storagePath + " directory cannot be deleted!!");
    }

    private void logDirectoryCannotBeCreatedError() {
        logErrorThenShutdown(storagePath + " directory cannot be created!!");
    }

    private void logErrorThenShutdown(String msg) {
        LoggerUtil.error(msg);
        LoggerUtil.error("Shutdown the whole application!!");
        System.exit(SpringApplication.exit(context));
    }
}
