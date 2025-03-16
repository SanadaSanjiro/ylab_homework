package website.ylab.financetracker.out.persistence.ram.budget;

import website.ylab.financetracker.auth.TrackerUser;
import website.ylab.financetracker.out.persistence.BudgetRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * In-memory repository implementation
 */
public class RamBudgetRepo implements BudgetRepository {
    private final Map<TrackerUser, Double> storage = new HashMap<>();
    @Override
    public Optional<Double> setBudget(TrackerUser user, double limit) {
        storage.put(user, limit);
        return Optional.of(limit);
    }

    @Override
    public Optional<Double> getBudget(TrackerUser user) {
        Double budget = storage.get(user);
        if (Objects.isNull(budget)) {
            return Optional.empty();
        }
        return Optional.of(budget);
    }

    @Override
    public Optional<Double> deleteBudget(TrackerUser user) {
        Double budget = storage.remove(user);
        if (Objects.isNull(budget)) {
            return Optional.empty();
        }
        return Optional.of(budget);
    }
}
