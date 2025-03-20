package website.ylab.financetracker.in.dto.budget;

public class BudgetRequest {
    private long userId;
    private double limit;

    public long getUserId() {
        return userId;
    }

    public BudgetRequest setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public double getLimit() {
        return limit;
    }

    public BudgetRequest setLimit(double limit) {
        this.limit = limit;
        return this;
    }
}
