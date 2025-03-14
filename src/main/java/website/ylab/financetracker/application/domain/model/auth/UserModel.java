package website.ylab.financetracker.application.domain.model.auth;

public class UserModel {
    private long id;
    private String username;
    private String password;
    private String email;
    private boolean enabled;
    private Role role;

    public long getId() {
        return id;
    }

    public UserModel setId(long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserModel setUsername(String username) {
        this.username = username.toLowerCase();
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserModel setEmail(String email) {
        this.email = email.toLowerCase();
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public UserModel setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public UserModel setRole(Role role) {
        this.role = role;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}