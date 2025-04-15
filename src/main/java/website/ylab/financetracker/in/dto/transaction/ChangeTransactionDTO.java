package website.ylab.financetracker.in.dto.transaction;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Used when changing transaction data
 */
@Accessors(chain = true)
@Data
public class ChangeTransactionDTO {
    private long id;
    private double amount;
    private String category;
    private String description;
}
