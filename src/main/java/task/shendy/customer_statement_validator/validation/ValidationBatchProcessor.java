package task.shendy.customer_statement_validator.validation;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task.shendy.customer_statement_validator.entity.FailedRecord;
import task.shendy.customer_statement_validator.entity.Record;
import task.shendy.customer_statement_validator.enums.ValidationError;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class ValidationBatchProcessor implements ItemProcessor<Record, FailedRecord> {

    //MARK: Properties

    private final ValidationJobProperties validationJobProperties;

    //MARK: Constructors

    @Autowired
    ValidationBatchProcessor(ValidationJobProperties validationJobProperties) {
        this.validationJobProperties = validationJobProperties;
    }

    //MARK: Overridden Methods

    @Override
    public FailedRecord process(Record record) throws Exception {
        List<ValidationError> errorList = new ArrayList<>();

        validationJobProperties.incrementProcessedRecordsByOne();

        if (isTransactionReferenceDuplicate(record)) {
            recordOneError(errorList, ValidationError.DUPLICATE_TRANSACTION_REFERENCE);
        }

        if (isEndBalanceInvalid(record)) {
            recordOneError(errorList, ValidationError.INVALID_END_BALANCE);
        }

        return errorList.isEmpty() ? null : new FailedRecord(record, errorList);
    }

    //MARK: Private Methods

    private void recordOneError(List<ValidationError> errorList, ValidationError validationError) {
        validationJobProperties.incrementErrorsByOne();
        errorList.add(validationError);
    }

    private boolean isTransactionReferenceDuplicate(Record record) {
        return !validationJobProperties.addToUniqueReferenceList(record.getReference());
    }

    private boolean isEndBalanceInvalid(Record record) {
        return record.getEndBalance() != Double.valueOf(roundTo2Digits(record.getStartBalance() + record.getMutation()));
    }

    private String roundTo2Digits(double number) {
        return new DecimalFormat("#####.##").format(number);
    }
}
