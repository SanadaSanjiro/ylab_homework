package website.ylab.financetracker.service.stat;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TurnoverRequest {
    private Date startDate;
    private long userid;
}
