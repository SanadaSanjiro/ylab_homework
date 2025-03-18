package website.ylab.financetracker.out.persistence.postgre.budget;

/**
 * A budget model used for data storage.
 */
public class BudgetEntity {
    private long id;
    private double limit;
    private long userId;
    private String uuid;

    public long getId() {
        return id;
    }

    public BudgetEntity setId(long id) {
        this.id = id;
        return this;
    }

    public double getLimit() {
        return limit;
    }

    public BudgetEntity setLimit(double limit) {
        this.limit = limit;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public BudgetEntity setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public BudgetEntity setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    @Override
    public String toString() {
        return "BudgetEntity{" +
                "id=" + id +
                ", limit=" + limit +
                ", userId=" + userId +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}