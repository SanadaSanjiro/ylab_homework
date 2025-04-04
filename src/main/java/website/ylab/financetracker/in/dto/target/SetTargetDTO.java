package website.ylab.financetracker.in.dto.target;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class SetTargetDTO {
    private double amount;
}
