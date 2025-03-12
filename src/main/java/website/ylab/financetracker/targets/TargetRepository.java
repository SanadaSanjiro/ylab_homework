package website.ylab.financetracker.targets;

import website.ylab.financetracker.auth.TrackerUser;

import java.util.Optional;

public interface TargetRepository {
    Optional<Double> setTarget(TrackerUser user, double amount);
    Optional<Double> getTarget(TrackerUser user);
    Optional<Double> deleteTarget(TrackerUser user);
}
