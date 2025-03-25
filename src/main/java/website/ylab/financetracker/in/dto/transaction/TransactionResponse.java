package website.ylab.financetracker.in.dto.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.experimental.Accessors;
import website.ylab.financetracker.util.CustomDateSerializer;

import java.util.Date;

@Accessors(chain = true)
@Data
public class TransactionResponse {
    private long id;
    private String type;
    private double amount;
    private String category;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date date;
    private String description;
    private long userId;
    private String uuid;
}
