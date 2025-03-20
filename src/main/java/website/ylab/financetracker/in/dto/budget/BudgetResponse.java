package website.ylab.financetracker.in.dto.budget;

public class BudgetResponse {
    private double limit;
    private String uuid;

    public double getLimit() {
        return limit;
    }

    public BudgetResponse setLimit(double limit) {
        this.limit = limit;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public BudgetResponse setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }
}
