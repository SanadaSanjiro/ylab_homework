package website.ylab.financetracker.in.dto.stat;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Used to provide a report
 */
@Accessors(chain = true)
@Data
public class ReportResponse {
    private BalanceResponse balance;
    private CategoryExpensesResponse category;
    private TurnoverResponse turnover;
}
