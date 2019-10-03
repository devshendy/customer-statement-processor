package task.shendy.customer_statement_validator.validation;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import task.shendy.customer_statement_validator.entity.FailedRecord;

import java.util.List;

@Component
public class ValidationBatchWriter implements ItemWriter<FailedRecord> {

    //MARK: Properties

    private final ValidationJobProperties validationJobProperties;

    //MARK: Constructors

    ValidationBatchWriter(ValidationJobProperties validationJobProperties) {
        this.validationJobProperties = validationJobProperties;
    }

    //MARK: Overridden Methods

    @Override
    public void write(List<? extends FailedRecord> list) throws Exception {
        validationJobProperties.addAllToFailedRecordList(list);
    }
}
