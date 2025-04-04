package website.ylab.financetracker.in.dto.budget;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class SetBudgetDTO {
    private double limit;
}