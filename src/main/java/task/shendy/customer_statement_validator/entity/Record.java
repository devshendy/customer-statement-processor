package task.shendy.customer_statement_validator.entity;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "record")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "reference", "accountNumber", "description", "startBalance", "mutation", "endBalance" })
public class Record {

    //MARK: Properties

    @XmlAttribute
    private String reference;

    @XmlElement(name = "accountNumber")
    private String accountNumber;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "startBalance")
    private double startBalance;

    @XmlElement(name = "mutation")
    private double mutation;

    @XmlElement(name = "endBalance")
    private double endBalance;

    //MARK: Constructors

    public Record() {}

    public Record(String reference, String accountNumber, double startBalance, double mutation, String description, double endBalance) {
        this.reference = reference;
        this.accountNumber = accountNumber;
        this.startBalance = startBalance;
        this.mutation = mutation;
        this.description = description;
        this.endBalance = endBalance;
    }

    //MARK: Getters and Setters

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getStartBalance() {
        return startBalance;
    }

    public void setStartBalance(double startBalance) {
        this.startBalance = startBalance;
    }

    public double getMutation() {
        return mutation;
    }

    public void setMutation(double mutation) {
        this.mutation = mutation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getEndBalance() {
        return endBalance;
    }

    public void setEndBalance(double endBalance) {
        this.endBalance = endBalance;
    }

    //MARK: Overridden Methods

    @Override
    public String toString() {
        return "record: {" +
                "\t reference: '" + this.reference + "'," +
                "\t account_number: '" + this.accountNumber + "'," +
                "\t description: '" + this.description + "'," +
                "\t start_balance: " + this.startBalance + "," +
                "\t mutation: " + this.mutation + "," +
                "\t end_balance: " + this.endBalance +
                "}";
    }
}
