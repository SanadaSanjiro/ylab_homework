package website.ylab.financetracker.out.persistence;

import website.ylab.financetracker.auth.TrackerUser;

import java.util.Optional;

public interface BudgetRepository {
    Optional<Double> setBudget(TrackerUser user, double limit);
    Optional<Double> getBudget(TrackerUser user);
    Optional<Double> deleteBudget(TrackerUser user);
}
