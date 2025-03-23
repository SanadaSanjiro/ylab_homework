package website.ylab.financetracker.in.dto.budget;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class BudgetResponse {
    private long id;
    private double limit;
    private long userId;
    private String uuid;
}
