package website.ylab.financetracker.in.dto.stat;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * Used to provide cost data grouped by category.
 */
@Accessors(chain = true)
@Data
public class CategoryExpensesResponse {
    Map<String, Double> expenses;
}
