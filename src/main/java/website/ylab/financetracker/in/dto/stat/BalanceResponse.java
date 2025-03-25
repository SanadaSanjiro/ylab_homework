package website.ylab.financetracker.in.dto.stat;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class BalanceResponse {
    private double income;
    private double outcome;
    private double balance;
}
