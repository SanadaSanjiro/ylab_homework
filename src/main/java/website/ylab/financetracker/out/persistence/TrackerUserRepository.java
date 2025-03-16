package website.ylab.financetracker.out.persistence;

import website.ylab.financetracker.auth.TrackerUser;

import java.util.List;
import java.util.Optional;

public interface TrackerUserRepository {
    Optional<TrackerUser> create(TrackerUser user);
    Optional<TrackerUser> get(String username);
    Optional<TrackerUser> update(TrackerUser user);
    Optional<TrackerUser> delete(TrackerUser user);
    List<TrackerUser> getAllUsers();
}