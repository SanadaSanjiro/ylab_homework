package website.ylab.financetracker.out.persistence.postgre.budget;

public class BudgetEntity {
    private long id;
    private double limit;
    private long userId;
    private String uuid;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
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
        return "BudgetEntity{" +
                "id=" + id +
                ", limit=" + limit +
                ", userId=" + userId +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
