package website.ylab.financetracker.in.dto.auth;

import website.ylab.financetracker.auth.Role;

public class UserResponse {
    private long id;
    private String name;
    private String email;
    private boolean enabled;
    private Role role;

    public long getId() {
        return id;
    }

    public UserResponse setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserResponse setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserResponse setEmail(String email) {
        this.email = email;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public UserResponse setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public UserResponse setRole(Role role) {
        this.role = role;
        return this;
    }
}
