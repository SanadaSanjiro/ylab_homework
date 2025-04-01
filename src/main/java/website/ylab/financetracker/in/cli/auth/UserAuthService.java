package website.ylab.financetracker.in.cli.auth;

import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.service.auth.UserService;
import website.ylab.financetracker.in.dto.auth.UserResponse;

/**
 * Performs user authentication
 */
public class UserAuthService {
    private final UserService userService;
    private static long currentUserId = 0L;

    public UserAuthService(UserService userService) {
        this.userService = userService;
    }

    public UserResponse login(TrackerUser userData) {
        if (userService.isValidCredentials(userData)) {
            return null;
        }
        UserResponse response = userService.getByName(userData.getUsername());
        currentUserId = response.getId();
        return response;
    }

    public static void logout() {
        currentUserId = 0L;
    }

    public static long getCurrentUserId() {
        return currentUserId;
    }
}