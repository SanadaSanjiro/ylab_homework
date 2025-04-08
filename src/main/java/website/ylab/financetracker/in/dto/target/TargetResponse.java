package website.ylab.financetracker.in.dto.target;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Used to return the results of target operations.
 */
@Accessors(chain = true)
@Data
public class TargetResponse {
    private long id;
    private double amount;
    private long userId;
    private String uuid;
}