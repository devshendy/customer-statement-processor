package task.shendy.customer_statement_validator.validation;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import task.shendy.customer_statement_validator.entity.Record;
import task.shendy.customer_statement_validator.entity.FailedRecord;

import static task.shendy.customer_statement_validator.controller.StorageController.storagePath;

@Configuration
public class ValidationBatchBeans {

    //MARK: Properties

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    //MARK: Constructors

    @Autowired
    public ValidationBatchBeans(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }


    //MARK: Batch Job Beans

    @Bean
    public Job validateCSVJob(Step validateCSVRecordsStep, ValidationJobListener validationJobListener) {
        return jobBuilderFactory.get("validateCSVJob")
                .incrementer(new RunIdIncrementer())
                .listener(validationJobListener)
                .flow(validateCSVRecordsStep)
                .end()
                .build();
    }

    @Bean
    public Job validateXMLJob(Step validateXMLRecordsStep, ValidationJobListener validationJobListener) {
        return jobBuilderFactory.get("validateXMLJob")
                .incrementer(new RunIdIncrementer())
                .listener(validationJobListener)
                .flow(validateXMLRecordsStep)
                .end()
                .build();
    }

    //Mark: Batch Step Beans

    @Bean
    public Step validateCSVRecordsStep(FlatFileItemReader<Record> csvReader, ValidationBatchProcessor processor, ValidationBatchWriter writer) {
        return stepBuilderFactory.get("validateCSVRecordsStep")
                .<Record, FailedRecord> chunk(10)
                .reader(csvReader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step validateXMLRecordsStep(StaxEventItemReader<Record> xmlReader, ValidationBatchProcessor processor, ValidationBatchWriter writer) {
        return stepBuilderFactory.get("validateXMLRecordsStep")
                .<Record, FailedRecord>chunk(10)
                .reader(xmlReader)
                .processor(processor)
                .writer(writer)
                .build();

    }

    //MARK: Batch Reader Beans

    @Bean
    @StepScope
    public FlatFileItemReader<Record> csvReader(@Value("#{jobParameters['storageFileName']}") String storageFileName) {
        return new FlatFileItemReaderBuilder<Record>()
                .name("csvReader")
                .resource(new FileSystemResource(storagePath + storageFileName))
                .delimited().delimiter(",")
                .names(new String[]{"Reference", "AccountNumber", "Description", "Start Balance", "Mutation", "End Balance"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Record>() {{
                    setTargetType(Record.class);
                }})
                .linesToSkip(1)
                .build();

    }

    @Bean
    @StepScope
    public StaxEventItemReader<Record> xmlReader(@Value("#{jobParameters['storageFileName']}") String storageFileName) {
        return new StaxEventItemReaderBuilder<Record>()
                .name("xmlReader")
                .resource(new FileSystemResource(storagePath + storageFileName))
                .addFragmentRootElements("record")
                .unmarshaller(recordJaxb2Marshaller())
                .build();
    }

    //MARK: Helper Beans

    @Bean
    public Jaxb2Marshaller recordJaxb2Marshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setClassesToBeBound(Record.class);
        return jaxb2Marshaller;
    }
}
