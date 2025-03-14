package website.ylab.financetracker.adapter.out.persistence.budget;

public class BudgetEntity {
    private long userId;
    private double limit;

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
}
