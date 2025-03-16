package website.ylab.financetracker.auth;

import website.ylab.financetracker.out.persistence.TrackerUserRepository;

import java.util.Optional;

/**
 * Performs user authentication
 */
public class UserAuthService {
    private final TrackerUserRepository trackerUserRepository;
    private static TrackerUser currentUser = null;

    public UserAuthService(TrackerUserRepository trackerUserRepository) {
        this.trackerUserRepository = trackerUserRepository;
    }

    public String login(String username, String password) {
        Optional<TrackerUser> optional= trackerUserRepository.getByName(username);
        if (optional.isEmpty()) {
            return "User not found";
        }
        TrackerUser user = optional.get();
        if (!user.getPassword().equals(password)) {
            return "Wrong password";
        }
        if (!user.isEnabled()) {
            return "Your are blocked. Contact the administrator";
        }
        currentUser = user;
        return "User " + username + " logged in";
    }

    public static void logout() {
        currentUser = null;
    }

    public static TrackerUser getCurrentUser() {
        return currentUser;
    }
}