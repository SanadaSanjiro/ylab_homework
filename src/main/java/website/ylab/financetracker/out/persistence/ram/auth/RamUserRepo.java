package website.ylab.financetracker.out.persistence.ram.auth;

import website.ylab.financetracker.auth.Role;
import website.ylab.financetracker.auth.TrackerUser;
import website.ylab.financetracker.out.persistence.TrackerUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    public Optional<TrackerUser> getByName(String username) {
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }

    @Override
    public Optional<TrackerUser> update(TrackerUser user) {
        long id = user.getId();
        Optional<TrackerUser> optional = users.stream()
                .filter(u -> u.getId() == id)
                .findFirst();
        if (optional.isEmpty()) {return Optional.empty(); }
        TrackerUser storedUser = optional.get();
        copyUserData(storedUser, user);
        return Optional.of(storedUser);
    }

    @Override
    public Optional<TrackerUser> delete(TrackerUser user) {
        if (users.contains(user)) {
            Optional<TrackerUser> deletedUser = getByName(user.getUsername());
            users.remove(user);
            return deletedUser;
        } else { return Optional.empty(); }
    }

    @Override
    public List<TrackerUser> getAllUsers() {
        return List.copyOf(users);
    }

    private void copyUserData(TrackerUser storedUser, TrackerUser newUser) {
        if (Objects.nonNull(newUser.getUsername())) {storedUser.setUsername(newUser.getUsername()); }
        if (Objects.nonNull(newUser.getPassword())) {storedUser.setPassword(newUser.getPassword()); }
        if (Objects.nonNull(newUser.getEmail())) {storedUser.setEmail(newUser.getEmail()); }
        if (Objects.nonNull(newUser.getRole())) {storedUser.setRole(newUser.getRole()); }
        storedUser.setEnabled(newUser.isEnabled());
    }

    private void addTestUser() {
        TrackerUser user = new TrackerUser("admin", "admin@admin.com", "123");
        user.setId(0);
        user.setRole(Role.ADMIN);
        user.setEnabled(true);
        users.add(user);
    }
}
