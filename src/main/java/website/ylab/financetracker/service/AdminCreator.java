package website.ylab.financetracker.service;

import org.springframework.stereotype.Service;
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.service.auth.Role;
import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.service.auth.UserService;

import java.util.Objects;

@Service
public class AdminCreator {
    private final UserService service;

    public AdminCreator(UserService service) {
        this.service = service;
    }

    public void createAdmin() {
        UserResponse response = service.getByName("admin");
        if (Objects.isNull(response)) {
            TrackerUser admin = new TrackerUser()
                    .setUsername("admin")
                    .setEmail("admin@admin.com")
                    .setPassword("123")
                    .setRole(Role.ADMIN)
                    .setEnabled(true);
            service.addNewUser(admin);
        }
    }
}
