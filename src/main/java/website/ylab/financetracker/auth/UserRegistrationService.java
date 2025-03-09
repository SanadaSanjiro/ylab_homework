package website.ylab.financetracker.auth;

import java.util.Optional;

import static website.ylab.financetracker.auth.UserDataVerificator.isUniqueEmail;
import static website.ylab.financetracker.auth.UserDataVerificator.isUniqueName;

public class UserRegistrationService {
    private final TrackerUserRepository trackerUserRepository;
    private static long userCounter=0L;

    public UserRegistrationService(TrackerUserRepository trackerUserRepository) {
        this.trackerUserRepository = trackerUserRepository;
    }

    public String addNewUser(String username, String email, String password) {
        if (!isUniqueName(trackerUserRepository, username)) {
            return "Username is already in use";
        }
        if (!isUniqueEmail(trackerUserRepository, email)) {
            return "Email is already in use";
        }
        TrackerUser user = getTrackerUser(username, email, password);
        Optional<TrackerUser> optional = trackerUserRepository.create(user);
        if (optional.isPresent()) {
            TrackerUser trackerUser = optional.get();
            return "User " +  trackerUser.getUsername() + " created with ID " + trackerUser.getId();
        } else {
            return "User creation error for name " + username;
        }
    }

    private TrackerUser getTrackerUser(String username, String email, String password) {
        TrackerUser user = new TrackerUser(username, email, password);
        user.setEnabled(true);
        user.setRole(Role.USER);
        user.setId(++userCounter);
        return user;
    }
}