package website.ylab.financetracker.out.repository;

import website.ylab.financetracker.service.budget.TrackerBudget;

import java.util.Optional;

public interface BudgetRepository {
    Optional<TrackerBudget> setBudget(TrackerBudget budget);
    Optional<TrackerBudget> getById(long id);
    Optional<TrackerBudget> getByUserId(long userId);
    Optional<TrackerBudget> deleteBudget(long id);
}
