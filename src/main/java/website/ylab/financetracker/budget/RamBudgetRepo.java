package website.ylab.financetracker.budget;

import website.ylab.financetracker.auth.TrackerUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * In-memory repository implementation
 */
public class RamBudgetRepo implements BudgetRepository{
    private final Map<TrackerUser, Double> storage = new HashMap<>();
    @Override
    public Optional<Double> setBudget(TrackerUser user, double amount) {
        storage.put(user, amount);
        return Optional.of(amount);
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
