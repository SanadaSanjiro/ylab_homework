package website.ylab.financetracker.out.repository.postgre.target;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class TargetEntity {
    private long id;
    private double amount;
    private long userId;
    private String uuid;
}