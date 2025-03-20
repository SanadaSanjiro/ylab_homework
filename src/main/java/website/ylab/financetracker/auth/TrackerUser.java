package website.ylab.financetracker.auth;

/**
 * Describes the user of the system
 */
public class TrackerUser {
    private long id;
    private String username;
    private String password;
    private String email;
    private boolean enabled;
    private Role role;

    public TrackerUser() {
    }

    public TrackerUser(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public TrackerUser setUsername(String username) {
        this.username = username.toLowerCase();
        return this;
    }

    public String getPassword() {
        return password;
    }

    public TrackerUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public TrackerUser setEmail(String email) {
        this.email = email.toLowerCase();
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public TrackerUser setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public TrackerUser setRole(Role role) {
        this.role = role;
        return this;
    }

    public long getId() {
        return id;
    }

    public TrackerUser setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "TrackerUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", role=" + role +
                "}\n";
    }
}