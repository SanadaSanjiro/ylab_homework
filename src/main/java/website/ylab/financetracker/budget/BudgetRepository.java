package website.ylab.financetracker.budget;

import website.ylab.financetracker.auth.TrackerUser;

import java.util.Optional;

public interface BudgetRepository {
    Optional<Double> setBudget(TrackerUser user, double amount);
    Optional<Double> getBudget(TrackerUser user);
    Optional<Double> deleteBudget(TrackerUser user);
}
