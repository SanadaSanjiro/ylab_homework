package website.ylab.financetracker.in.dto.auth;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Used when creating and modifying a user.
 */
@Accessors(chain = true)
@Data
public class UserDataDTO {
    private String username;
    private String password;
    private String email;
}
