package website.ylab.financetracker.in.dto.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;
    private String description;
    private long userId;
    private String uuid;
}
