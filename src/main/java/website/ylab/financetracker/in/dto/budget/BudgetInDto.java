package website.ylab.financetracker.in.dto.budget;

public class BudgetInDto {
    private long userId;
    private double limit;

    public long getUserId() {
        return userId;
    }

    public BudgetInDto setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public double getLimit() {
        return limit;
    }

    public BudgetInDto setLimit(double limit) {
        this.limit = limit;
        return this;
    }
}
