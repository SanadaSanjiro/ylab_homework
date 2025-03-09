package website.ylab.financetracker.auth;

import java.util.List;
import java.util.Optional;

public interface TrackerUserRepository {
    public Optional<TrackerUser> create(TrackerUser user);
    public Optional<TrackerUser> get(String username);
    public Optional<TrackerUser> update(TrackerUser oldUser, TrackerUser newUser);
    public Optional<TrackerUser> delete(TrackerUser user);
    public List<TrackerUser> getAllUsers();
}