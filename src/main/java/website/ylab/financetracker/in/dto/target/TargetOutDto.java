package website.ylab.financetracker.in.dto.target;

public class TargetOutDto {
    private double amount;
    private String uuid;

    public double getAmount() {
        return amount;
    }

    public TargetOutDto setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public TargetOutDto setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }
}
