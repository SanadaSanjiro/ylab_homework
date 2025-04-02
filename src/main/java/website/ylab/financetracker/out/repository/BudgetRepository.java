package website.ylab.financetracker.out.repository;

import website.ylab.financetracker.service.budget.TrackerBudget;

import java.util.Optional;

/**
 * Describes methods for working with budget entities
 */
public interface BudgetRepository {
    /**
     * Sets a new user budget. If the user already had a budget, the old one must be deleted beforehand
     * @param budget TrackerBudget new budget value
     * @return Optional<TrackerBudget> on success or an empty Optional on failure
     */
    Optional<TrackerBudget> setBudget(TrackerBudget budget);

    /**
     * Gets budget value by its ID
     * @param id long budget ID
     * @return Optional<TrackerBudget> on success, or an empty Optional if nothing was found
     */
    Optional<TrackerBudget> getById(long id);

    /**
     * Gets the budget value by its owner ID
     * @param userId long user ID
     * @return Optional<TrackerBudget> on success, or an empty Optional if nothing was found
     */
    Optional<TrackerBudget> getByUserId(long userId);

    /**
     * Deletes budget from a storage
     * @param id long budget ID
     * @return Optional<TrackerBudget> on success or an empty Optional on failure
     */
    Optional<TrackerBudget> deleteBudget(long id);
}