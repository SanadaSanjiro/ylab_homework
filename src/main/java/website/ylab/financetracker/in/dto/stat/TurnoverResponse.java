package website.ylab.financetracker.in.dto.stat;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Used to provide turnover data for a period
 */
@Accessors(chain = true)
@Data
public class TurnoverResponse {
    private double income;
    private double outcome;
}