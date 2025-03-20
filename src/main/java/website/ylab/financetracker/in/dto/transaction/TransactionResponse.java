package website.ylab.financetracker.in.dto.transaction;

import website.ylab.financetracker.transactions.TransactionType;

import java.util.Date;

public class TransactionResponse {
    private long id;
    private TransactionType type;
    private double amount;
    private String category;
    private Date date;
    private String description;
    private long userId;
    private String uuid;

    public long getId() {
        return id;
    }

    public TransactionResponse setId(long id) {
        this.id = id;
        return this;
    }

    public TransactionType getType() {
        return type;
    }

    public TransactionResponse setType(TransactionType type) {
        this.type = type;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionResponse setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public TransactionResponse setCategory(String category) {
        this.category = category;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public TransactionResponse setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TransactionResponse setDescription(String description) {
        this.description = description;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public TransactionResponse setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public TransactionResponse setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }
}
