package website.ylab.financetracker.service.targets;


import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class TrackerTarget {
    private long id;
    private double amount;
    private long userId;
    private String uuid;
}
