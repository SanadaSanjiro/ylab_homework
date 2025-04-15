package website.ylab.financetracker.in.dto.auth;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Used when changing a user's role
 */
@Accessors(chain = true)
@Data
public class RoleDTO {
    private long userid;
    private String role;
}
