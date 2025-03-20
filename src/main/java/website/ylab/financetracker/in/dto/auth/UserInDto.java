package website.ylab.financetracker.in.dto.auth;

public class UserInDto {
    private String name;
    private String email;
    private String password;

    public String getName() {
        return name;
    }

    public UserInDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserInDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserInDto setPassword(String password) {
        this.password = password;
        return this;
    }
}
