package website.ylab.financetracker.targets;

import website.ylab.financetracker.auth.TrackerUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * In-memory repository implementation
 */

public class RamTargetRepo implements TargetRepository{
    private final Map<TrackerUser, Double> storage = new HashMap<>();

    @Override
    public Optional<Double> setTarget(TrackerUser user, double amount) {
        storage.put(user, amount);
        return Optional.of(amount);
    }

    @Override
    public Optional<Double> getTarget(TrackerUser user) {
        Double target = storage.get(user);
        if (Objects.isNull(target)) {
            return Optional.empty();
        }
        return Optional.of(target);
    }

    @Override
    public Optional<Double> deleteTarget(TrackerUser user) {
        Double target = storage.remove(user);
        if (Objects.isNull(target)) {
            return Optional.empty();
        }
        return Optional.of(target);
    }
}
