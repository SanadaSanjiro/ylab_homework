package website.ylab.financetracker.service.stat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import website.ylab.financetracker.util.CustomDateSerializer;

import java.util.Date;

@Getter
@Setter
public class TurnoverRequest {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date startDate;
    private long userid;
}
