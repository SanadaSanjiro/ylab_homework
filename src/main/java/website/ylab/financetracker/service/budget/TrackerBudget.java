package website.ylab.financetracker.service.budget;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Budget Model
 */
@Accessors(chain = true)
@Data
public class TrackerBudget {
    private long id;
    private double limit;
    private long userId;
    private String uuid;
}