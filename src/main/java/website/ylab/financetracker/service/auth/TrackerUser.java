package website.ylab.financetracker.service.auth;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Describes the user of the system
 */
@Accessors(chain = true)
@Data
public class TrackerUser {
    private long id;
    private String username;
    private String password;
    private String email;
    private boolean enabled;
    private Role role;
}