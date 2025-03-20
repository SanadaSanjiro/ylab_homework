package website.ylab.financetracker.in.dto.target;

public class TargetInDto {
    private long userId;
    private double amount;

    public long getUserId() {
        return userId;
    }

    public TargetInDto setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public TargetInDto setAmount(double amount) {
        this.amount = amount;
        return this;
    }
}
