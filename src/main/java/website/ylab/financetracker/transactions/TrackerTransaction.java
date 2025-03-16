package website.ylab.financetracker.transactions;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Transaction
 */
public class TrackerTransaction {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
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

    public void setId(long id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "TrackerTransaction{" +
                "id=" + id +
                ", type=" + type +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                ", date=" + date + //dateFormat.format(date) +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}