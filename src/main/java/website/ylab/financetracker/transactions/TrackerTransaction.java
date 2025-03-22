package website.ylab.financetracker.transactions;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Transaction model
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

    public TrackerTransaction setId(long id) {
        this.id = id;
        return this;
    }

    public TransactionType getType() {
        return type;
    }

    public TrackerTransaction setType(TransactionType type) {
        this.type = type;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public TrackerTransaction setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public TrackerTransaction setCategory(String category) {
        this.category = category;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public TrackerTransaction setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TrackerTransaction setDescription(String description) {
        this.description = description;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public TrackerTransaction setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public TrackerTransaction setUuid(String uuid) {
        this.uuid = uuid;
        return this;
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
                "}\n";
    }
}