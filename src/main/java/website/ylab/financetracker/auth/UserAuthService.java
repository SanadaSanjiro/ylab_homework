package website.ylab.financetracker.auth;

import website.ylab.financetracker.in.dto.auth.UserMapper;
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.out.persistence.TrackerUserRepository;

import java.util.Optional;

/**
 * Performs user authentication
 */
public class UserAuthService {
    private final TrackerUserRepository trackerUserRepository;
    private static TrackerUser currentUser = null;
    private static UserMapper mapper = UserMapper.INSTANCE;

    public UserAuthService(TrackerUserRepository trackerUserRepository) {
        this.trackerUserRepository = trackerUserRepository;
    }

    public UserResponse login(TrackerUser userData) {
        Optional<TrackerUser> optional= trackerUserRepository.getByName(userData.getUsername());
        if (optional.isEmpty()) {
            return null;
        }
        TrackerUser user = optional.get();
        if (!user.getPassword().equals(userData.getPassword())) {
            return null;
        }
        if (!user.isEnabled()) {
            return null;
        }
        currentUser = user;
        return mapper.toResponse(currentUser);
    }

    public static void logout() {
        currentUser = null;
    }

    public static TrackerUser getCurrentUser() {
        return currentUser;
    }
}