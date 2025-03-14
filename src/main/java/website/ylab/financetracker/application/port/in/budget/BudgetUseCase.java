package website.ylab.financetracker.application.port.in.budget;

import website.ylab.financetracker.application.domain.model.budget.BudgetModel;

public interface BudgetUseCase {
    /**
     * Budget creation operation
     * @param budget Budget for creation
     * @return Optional<BudgetModel> with a new budget or empty Optional if the operation failed
     */
    BudgetModel create(BudgetModel budget);

    /**
     * Get budget of current user
     * @return Optional<BudgetModel> if successful or empty Optional if no budget with that id was found
     */
    BudgetModel get();

    /**
     * Delete budget of current user
     * @return Optional<BudgetModel> if successful
     *      * or empty Optional if no budget with that ID was found to delete.
     */
    BudgetModel delete();

    /**
     * Checks if the current user's budget has been exceeded.
     * @return true if the sum of EXPENSE transactions in the last month is greater than the budget limit
     */
    boolean isExceeded();
}