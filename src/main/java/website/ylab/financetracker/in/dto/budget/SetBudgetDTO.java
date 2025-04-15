package website.ylab.financetracker.in.dto.budget;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Used to set a new budget value.
 */
@Accessors(chain = true)
@Data
public class SetBudgetDTO {
    private double limit;
}