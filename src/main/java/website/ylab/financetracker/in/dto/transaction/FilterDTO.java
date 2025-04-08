package website.ylab.financetracker.in.dto.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Used to set filters by type, category and date when retrieving a filtered list of transactions
 */
@Accessors(chain = true)
@Data
public class FilterDTO {
    private String type;
    private String category;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date date;
}
