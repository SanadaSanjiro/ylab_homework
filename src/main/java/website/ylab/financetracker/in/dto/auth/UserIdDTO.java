package website.ylab.financetracker.in.dto.auth;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Used in numerous methods that require a user ID to be specified.
 */
@Accessors(chain = true)
@Data
public class UserIdDTO {
    private long userid;
}
