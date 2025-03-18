package website.ylab.financetracker.out.persistence.postgre.target;

public class TargetEntity {
    private long id;
    private double amount;
    private long userId;
    private String uuid;

    public long getId() {
        return id;
    }

    public TargetEntity setId(long id) {
        this.id = id;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public TargetEntity setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public TargetEntity setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public TargetEntity setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }
}