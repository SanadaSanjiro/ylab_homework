package website.ylab.financetracker.in.dto.stat;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Used to provide balance data
 */
@Accessors(chain = true)
@Data
public class BalanceResponse {
    private double income;
    private double outcome;
    private double balance;
}
