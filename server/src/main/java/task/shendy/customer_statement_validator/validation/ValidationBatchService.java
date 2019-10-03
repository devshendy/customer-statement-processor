package task.shendy.customer_statement_validator.validation;

import org.apache.commons.io.FilenameUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task.shendy.customer_statement_validator.util.LoggerUtil;

import java.util.Timer;
import java.util.TimerTask;

@Component
public class ValidationBatchService {

    //MARK: Properties

    private Job validateXMLJob;
    private Job validateCSVJob;
    private JobLauncher jobLauncher;
    private final Timer timer = new Timer();

    //MARK: Constructors

    @Autowired
    public ValidationBatchService(Job validateCSVJob, Job validateXMLJob, JobLauncher jobLauncher) {
        this.validateCSVJob = validateCSVJob;
        this.validateXMLJob = validateXMLJob;
        this.jobLauncher = jobLauncher;
    }

    //MARK: Public Methods

    public void run(String fileName) {
        long delay = 3000;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    runJobLauncher(fileName, buildJobParameters(fileName));

                } catch (JobExecutionAlreadyRunningException
                        | JobRestartException
                        | JobInstanceAlreadyCompleteException
                        | JobParametersInvalidException e) { // TODO: Handle exception
                    LoggerUtil.error(e.getMessage());
                }
            }
        }, delay);

        LoggerUtil.info("Run validation batch service after " + delay + " seconds.");
    }

    //MARK: Private Methods

    private void runJobLauncher(String fileName, JobParameters jobParameters) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        switch (getFileExtension(fileName)) {
            case "csv":
                jobLauncher.run(validateCSVJob, jobParameters);
                break;

            case "xml":
                jobLauncher.run(validateXMLJob, jobParameters);
                break;
        }
    }

    private String getFileExtension(String fileName) {
        return FilenameUtils.getExtension(fileName).toLowerCase();
    }

    private JobParameters buildJobParameters(String storageFileName) {
        return new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .addString("storageFileName", storageFileName)
                .toJobParameters();
    }
}
