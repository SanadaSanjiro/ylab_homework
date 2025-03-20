package website.ylab.financetracker.in.dto.target;

public class TargetResponse {
    private double amount;
    private String uuid;

    public double getAmount() {
        return amount;
    }

    public TargetResponse setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public TargetResponse setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }
}
