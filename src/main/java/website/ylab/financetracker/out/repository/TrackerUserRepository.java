package website.ylab.financetracker.out.repository;

import website.ylab.financetracker.service.auth.TrackerUser;

import java.util.List;
import java.util.Optional;

/**
 * Repository implementation must create admin user with role=Role.ADMIN, name=admin, password=123, isEnabled = true
 * and non-empty email
 */
public interface TrackerUserRepository {
    Optional<TrackerUser> create(TrackerUser user);
    Optional<TrackerUser> getByName(String username);
    Optional<TrackerUser> getById(long id);
    Optional<TrackerUser> update(TrackerUser user);
    Optional<TrackerUser> delete(TrackerUser user);
    List<TrackerUser> getAllUsers();
}