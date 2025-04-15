package website.ylab.financetracker.in.dto.auth;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Used for authentication in the system
 */
@Accessors(chain = true)
@Data
public class LoginDTO {
    private String username;
    private String password;
}
