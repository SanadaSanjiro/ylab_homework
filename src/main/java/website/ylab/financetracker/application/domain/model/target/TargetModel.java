package website.ylab.financetracker.application.domain.model.target;

public class TargetModel {
    private long userId;
    private double targetAmount;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public boolean isReached(double monthIncome) {
        return monthIncome >= targetAmount;
    }

    public double getShortage(double monthIncome) {
        return targetAmount -monthIncome;
    }
}
