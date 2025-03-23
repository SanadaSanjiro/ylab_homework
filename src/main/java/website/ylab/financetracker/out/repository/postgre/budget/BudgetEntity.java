package website.ylab.financetracker.out.repository.postgre.budget;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A budget model used for data storage.
 */
@Accessors(chain = true)
@Data
public class BudgetEntity {
    private long id;
    private double limit;
    private long userId;
    private String uuid;
}