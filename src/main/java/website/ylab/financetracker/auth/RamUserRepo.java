package website.ylab.financetracker.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * In-memory repository implementation
 */

public class RamUserRepo implements TrackerUserRepository {
    private final List<TrackerUser> users = new ArrayList<>();

    public RamUserRepo() {
        addTestUser();
    }

    @Override
    public Optional<TrackerUser> create(TrackerUser user) {
        users.add(user);
        return Optional.of(user);
    }

    @Override
    public Optional<TrackerUser> get(String username) {
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }

    @Override
    public Optional<TrackerUser> update(TrackerUser oldUser, TrackerUser newUser) {
        if (users.contains(oldUser)) {
            copyUserData(oldUser, newUser);
            return get(newUser.getUsername());
        } else { return Optional.empty(); }
    }

    @Override
    public Optional<TrackerUser> delete(TrackerUser user) {
        if (users.contains(user)) {
            Optional<TrackerUser> deletedUser = get(user.getUsername());
            users.remove(user);
            return deletedUser;
        } else { return Optional.empty(); }
    }

    @Override
    public List<TrackerUser> getAllUsers() {
        return List.copyOf(users);
    }

    private void copyUserData(TrackerUser oldUser, TrackerUser newUser) {
        oldUser.setUsername(newUser.getUsername());
        oldUser.setPassword(newUser.getPassword());
        oldUser.setEmail(newUser.getEmail());
        oldUser.setRole(newUser.getRole());
        oldUser.setEnabled(newUser.isEnabled());
    }

    private void addTestUser() {
        TrackerUser user = new TrackerUser("admin", "admin@admin.com", "123");
        user.setId(0);
        user.setRole(Role.ADMIN);
        user.setEnabled(true);
        users.add(user);
    }
}
