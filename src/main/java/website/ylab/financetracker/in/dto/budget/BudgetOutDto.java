package website.ylab.financetracker.in.dto.budget;

public class BudgetOutDto {
    private double limit;
    private String uuid;

    public double getLimit() {
        return limit;
    }

    public BudgetOutDto setLimit(double limit) {
        this.limit = limit;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public BudgetOutDto setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }
}
