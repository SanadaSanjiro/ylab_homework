package website.ylab.financetracker.application.domain.model.transaction;

import website.ylab.financetracker.transactions.TransactionType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionModel {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private long id;
    private TransactionType type;
    private double amount;
    private String category;
    private Date date;
    private String description;
    private long userId;

    public long getId() {
        return id;
    }

    public TransactionModel setId(long id) {
        this.id = id;
        return this;
    }

    public TransactionType getType() {
        return type;
    }

    public TransactionModel setType(TransactionType type) {
        this.type = type;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionModel setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public TransactionModel setCategory(String category) {
        this.category = category;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public TransactionModel setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TransactionModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public TransactionModel setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String toString() {
        return "TransactionModel{" +
                "id=" + id +
                ", type=" + type +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                '}';
    }
}
