package website.ylab.financetracker.in.dto.transaction;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class TransactionResponse {
    private long id;
    private String type;
    private double amount;
    private String category;
    private Date date;
    private String description;
    private long userId;
    private String uuid;
}
