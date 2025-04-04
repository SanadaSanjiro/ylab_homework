package website.ylab.financetracker.in.dto.transaction;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class ChangeTransactionDTO {
    private double amount;
    private String category;
    private String description;
}
