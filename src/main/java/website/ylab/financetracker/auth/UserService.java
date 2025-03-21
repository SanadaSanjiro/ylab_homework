package website.ylab.financetracker.auth;

import website.ylab.financetracker.ServiceProvider;
import website.ylab.financetracker.in.dto.auth.UserMapper;
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.out.persistence.TrackerUserRepository;

import java.util.List;
import java.util.Optional;

import static website.ylab.financetracker.auth.UserDataVerificator.isUniqueEmail;
import static website.ylab.financetracker.auth.UserDataVerificator.isUniqueName;

/**
 * Provides methods for changing user data.
 */
public class UserService {
    private final TrackerUserRepository trackerUserRepository;
    private final UserMapper mapper = UserMapper.INSTANCE;

    public UserService(TrackerUserRepository trackerUserRepository) {
        this.trackerUserRepository = trackerUserRepository;
    }
    public UserResponse changeUser(TrackerUser newUser) {
        TrackerUser oldUser = UserAuthService.getCurrentUser();

        if (!oldUser.getUsername().equals(newUser.getUsername())) {
            if (!isUniqueName(trackerUserRepository, newUser.getUsername())) {
                return null;
            } else oldUser.setUsername(newUser.getUsername());
        }

        if (!oldUser.getEmail().equals(newUser.getEmail())) {
            if (!isUniqueEmail(trackerUserRepository, newUser.getEmail())) {
                return null;
            } else oldUser.setEmail(newUser.getEmail());
        }

        if (!oldUser.getPassword().equals(newUser.getPassword())) {
            oldUser.setPassword(newUser.getPassword());
        }
        Optional<TrackerUser> optional = trackerUserRepository.update(oldUser);
        return optional.map(mapper::toResponse).orElse(null);
    }

    public List<UserResponse> getAllUsersResponse() {
        return mapper.toUserResponseList(trackerUserRepository.getAllUsers());
    }

    public List<TrackerUser> getAllUsers() {
        return trackerUserRepository.getAllUsers();
    }

    /**
     * Removes a user from the system. Also deletes all of their data.
     * @return UserResponse with a deleted user o null if failed.
     */
    public UserResponse deleteCurrentUser() {
        TrackerUser user = UserAuthService.getCurrentUser();
        UserAuthService.logout();
        return deleteUser(user.getId());
    }


    /**
     * Get user by id
     * @param id long id
     * @return UserResponse or null if not found
     */
    public UserResponse getById(long id) {
        Optional<TrackerUser> optional = trackerUserRepository.getById(id);
        return optional.map(mapper::toResponse).orElse(null);
    }

    /**
     * Removes a user from the system. Also deletes all of their data.
     * @param id long user id to delete
     * @return String with a result.
     */
    public UserResponse deleteUser(long id) {
        Optional<TrackerUser> optional = trackerUserRepository.getById(id);
        if (optional.isEmpty()) return null;
        TrackerUser storedUser = optional.get();
        ServiceProvider.getTransactionService().deleteUserTransactions(storedUser);
        ServiceProvider.getBudgetService().deleteBudget(storedUser);
        ServiceProvider.getTargetService().deleteTarget(storedUser);
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
}