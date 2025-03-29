package website.ylab.financetracker.service.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.ylab.financetracker.in.dto.auth.UserMapper;
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.out.repository.TrackerUserRepository;

import java.util.List;
import java.util.Optional;

/**
 * Provides methods for changing user data.
 */
@Service
public class UserService {
    private final TrackerUserRepository trackerUserRepository;
    private final UserMapper mapper = UserMapper.INSTANCE;
    Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    public UserService(TrackerUserRepository trackerUserRepository) {
        this.trackerUserRepository = trackerUserRepository;
    }

    /**
     * Changes username, password and email. The input must be completely valid.
     * The username and email fields must be not empty and unique.
     * @param newUserData TrackerUser object with non-empty valid username, email and password fields
     * @return UserResponse if success or null if failed
     */
    public UserResponse changeUser(TrackerUser newUserData) {
        Optional<TrackerUser> optional = trackerUserRepository.update(newUserData);
        return optional.map(mapper::toResponse).orElse(null);
    }

    /**
     * Creates new user The input must be completely valid.
     * The username and email fields must be not empty and unique.
     * @param newUser TrackerUser object with non-empty valid username, email and password fields
     * @return UserResponse if success or null if failed
     */
    public UserResponse addNewUser(TrackerUser newUser) {
        newUser.setEnabled(true);
        newUser.setRole(Role.USER);
        Optional<TrackerUser> optional = trackerUserRepository.create(newUser);
        if (optional.isPresent()) {
            TrackerUser trackerUser = optional.get();
            logger.info("User created {}", trackerUser.toString());
            return mapper.toResponse(trackerUser);
        } else {
            logger.warn("User {} not created", newUser);
            return null;
        }
    }

    /**
     * Checks if the entered name is unique
     * @param name String name
     * @return boolean true if the name is unique
     */
    public boolean isUniqueName(String name) {
        return trackerUserRepository.getAllUsers().stream().noneMatch(trackerUser
                -> trackerUser.getUsername().equals(name.toLowerCase()));
    }

    /**
     * Checks if the entered email is unique
     * @param email String email
     * @return boolean true if the email is unique
     */
    public boolean isUniqueEmail(String email) {
        return trackerUserRepository.getAllUsers().stream().noneMatch(trackerUser
                -> trackerUser.getEmail().equals(email.toLowerCase()));
    }

    /**
     * Checks that the username and password provided are correct.
     * @param newUser TrackerUser object with non-empty valid username and password fields
     * @return boolean true if user with such name exists and his password equals provided password value
     */
    public boolean isValidCredentials(TrackerUser newUser) {
        Optional<TrackerUser> optional = trackerUserRepository.getByName(newUser.getUsername());
        return optional.filter(user -> newUser.getPassword().equals(user.getPassword())).isPresent();
    }

    /**
     * Checks if user is enabled.
     * @param username String user name
     * @return boolean false if user not exist or blocked
     */
    public boolean isEnabled(String username) {
        Optional<TrackerUser> optional = trackerUserRepository.getByName(username);
        return optional.map(TrackerUser::isEnabled).orElse(false);
    }

    /**
     * Get all users response list
     * @return List<UserResponse> with all registered users
     */
    public List<UserResponse> getAllUsersResponse() {
        return mapper.toUserResponseList(trackerUserRepository.getAllUsers());
    }

    /**
     * Get all TrackerUsers
     * @return List<TrackerUser> with all registered users
     */
    public List<TrackerUser> getAllUsers() {
        return trackerUserRepository.getAllUsers();
    }

    /**
     * Get user response by id
     * @param id long id
     * @return UserResponse or null if not found
     */
    public UserResponse getResponseById(long id) {
        Optional<TrackerUser> optional = trackerUserRepository.getById(id);
        return optional.map(mapper::toResponse).orElse(null);
    }

    /**
     * Get user by id
     * @param id long id
     * @return Optional<TrackerUser>
     */
    public Optional<TrackerUser> getById(long id) {
        return trackerUserRepository.getById(id);
    }

    /**
     * Get user by name
     * @param name String name
     * @return UserResponse or null if not found
     */
    public UserResponse getByName(String name) {
        Optional<TrackerUser> optional = trackerUserRepository.getByName(name);
        return optional.map(mapper::toResponse).orElse(null);
    }

    /**
     * Removes a user from the system. Users data must be previously deleted using UserDataDeletionService.
     * @param id long user id to delete
     * @return String with a result.
     */
    public UserResponse deleteUser(long id) {
        Optional<TrackerUser> optional = trackerUserRepository.getById(id);
        if (optional.isEmpty()) return null;
        TrackerUser storedUser = optional.get();
        optional = trackerUserRepository.delete(storedUser);
        return optional.map(mapper::toResponse).orElse(null);
    }

    /**
     * blocking user
     * @param id long user id to block
     * @return  UserResponse if success, null if failed
     */
    public UserResponse blockUser(long id) {
        Optional<TrackerUser> optional = trackerUserRepository.getById(id);
        if (optional.isEmpty()) return null;
        TrackerUser user = optional.get();
        user.setEnabled(false);
        optional = trackerUserRepository.update(user);
        return optional.map(mapper::toResponse).orElse(null);
    }

    /**
     * unblocking user
     * @param id long user id to unblock
     * @return  UserResponse if success, null if failed
     */
    public UserResponse unblockUser(long id) {
        Optional<TrackerUser> optional = trackerUserRepository.getById(id);
        if (optional.isEmpty()) return null;
        TrackerUser user = optional.get();
        user.setEnabled(true);
        optional = trackerUserRepository.update(user);
        return optional.map(mapper::toResponse).orElse(null);
    }

    /**
     * changing user's role
     * @param id long user id to change role
     * @param role new Role values
     * @return UserResponse if success, null if failed
     */
    public UserResponse changeUserRole(long id, Role role) {
        Optional<TrackerUser> optional = trackerUserRepository.getById(id);
        if (optional.isEmpty()) return null;
        TrackerUser user = optional.get();
        user.setRole(role);
        optional = trackerUserRepository.update(user);
        return optional.map(mapper::toResponse).orElse(null);
    }

    /**
     * Checks users credentials
     * @param user TrackingUser with username and password
     * @return boolean true if user with such name exists and provided password is equals stored password
     */
    public boolean checkPassword(TrackerUser user) {
        Optional <TrackerUser> optional = trackerUserRepository.getByName(user.getUsername());
        if (optional.isEmpty()) { return false; } ;
        return user.getPassword().equals(optional.get().getPassword());
    }
}