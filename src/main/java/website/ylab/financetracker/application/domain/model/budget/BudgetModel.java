package website.ylab.financetracker.application.domain.model.budget;

public class BudgetModel {
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

    public boolean isExceeded(double monthExpenses) {
        return monthExpenses>limit;
    }

    public double getBudgetBalance(double monthExpenses) {
        return limit-monthExpenses;
    }
}