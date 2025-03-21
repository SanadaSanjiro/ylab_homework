package website.ylab.financetracker.auth;

import website.ylab.financetracker.in.dto.auth.UserMapper;
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.out.persistence.TrackerUserRepository;

import java.util.Optional;

import static website.ylab.financetracker.auth.UserDataVerificator.isUniqueEmail;
import static website.ylab.financetracker.auth.UserDataVerificator.isUniqueName;

public class UserRegistrationService {
    private final TrackerUserRepository trackerUserRepository;
    private static long userCounter=0L;
    private final UserMapper mapper = UserMapper.INSTANCE;

    public UserRegistrationService(TrackerUserRepository trackerUserRepository) {
        this.trackerUserRepository = trackerUserRepository;
    }

    public UserResponse addNewUser(TrackerUser newUser) {
        if (!isUniqueName(trackerUserRepository, newUser.getUsername())) {
            return null;
        }
        if (!isUniqueEmail(trackerUserRepository, newUser.getEmail())) {
            return null;
        }
        newUser.setEnabled(true);
        newUser.setRole(Role.USER);
        Optional<TrackerUser> optional = trackerUserRepository.create(newUser);
        if (optional.isPresent()) {
            TrackerUser trackerUser = optional.get();
            return mapper.toResponse(trackerUser);
        } else {
            return null;
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