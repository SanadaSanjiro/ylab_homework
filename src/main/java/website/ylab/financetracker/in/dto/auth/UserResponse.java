package website.ylab.financetracker.in.dto.auth;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Used to return results of operations on users.
 */
@Accessors(chain = true)
@Data
public class UserResponse {
    private long id;
    private String name;
    private String email;
    private boolean enabled;
    private String role;
}
