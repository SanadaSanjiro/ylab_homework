package website.ylab.financetracker.service.transactions;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * Transaction model
 */
@Accessors(chain = true)
@Data
public class TrackerTransaction {
    private long id;
    private TransactionType type;
    private double amount;
    private String category;
    private Date date;
    private String description;
    private long userId;
    private String uuid;
}