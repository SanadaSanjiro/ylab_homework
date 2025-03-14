package website.ylab.financetracker.application.port.out.budget;

import website.ylab.financetracker.application.domain.model.budget.BudgetModel;

import java.util.Optional;

/**
 * Operations with budgets
 */
public interface ExternalBudgetStorage {
    /**
     * Create budgets operation
     * @param budget BudgetModel for creation
     * @return Optional<BudgetModel> with a new budgets or empty Optional if the operation failed
     */
    Optional<BudgetModel> create(BudgetModel budget);

    /**
     * Get budgets by id
     * @param id long budget id, must be equal id of user, that created this budget
     * @return Optional<BudgetModel> if successful or empty Optional if no budget with that id was found
     */
    Optional<BudgetModel> getById(long id);

    /**
     * Deleting a budgets
     * @param id long budget's number
     * @return Optional<BudgetModel> if successful
     *      *  or empty Optional if no budget with that ID was found to delete.
     */
    Optional<BudgetModel>delete(long id);
}