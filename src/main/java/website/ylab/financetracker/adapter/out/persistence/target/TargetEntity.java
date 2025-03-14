package website.ylab.financetracker.adapter.out.persistence.target;

public class TargetEntity {
    private long userId;
    private double target;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getTarget() {
        return target;
    }

    public void setTarget(double target) {
        this.target = target;
    }
}