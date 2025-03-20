package website.ylab.financetracker.in.dto.target;

public class TargetRequest {
    private long userId;
    private double amount;

    public long getUserId() {
        return userId;
    }

    public TargetRequest setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public TargetRequest setAmount(double amount) {
        this.amount = amount;
        return this;
    }
}
