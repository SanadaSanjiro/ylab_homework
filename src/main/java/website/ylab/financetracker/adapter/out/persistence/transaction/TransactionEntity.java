package website.ylab.financetracker.adapter.out.persistence.transaction;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionEntity {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private long id;
    private String type;
    private double amount;
    private String category;
    private Date date;
    private String description;
    private long userId;

    public long getId() {
        return id;
    }

    public TransactionEntity setId(long id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public TransactionEntity setType(String type) {
        this.type = type;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionEntity setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public TransactionEntity setCategory(String category) {
        this.category = category;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public TransactionEntity setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TransactionEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public TransactionEntity setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String toString() {
        return "TransactionEntity{" +
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