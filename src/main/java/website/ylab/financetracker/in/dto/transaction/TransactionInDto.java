package website.ylab.financetracker.in.dto.transaction;

import website.ylab.financetracker.transactions.TransactionType;

import java.util.Date;

public class TransactionInDto {
    private TransactionType type;
    private double amount;
    private String category;
    private Date date;
    private String description;

    public TransactionType getType() {
        return type;
    }

    public TransactionInDto setType(TransactionType type) {
        this.type = type;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionInDto setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public TransactionInDto setCategory(String category) {
        this.category = category;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public TransactionInDto setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TransactionInDto setDescription(String description) {
        this.description = description;
        return this;
    }
}
