package website.ylab.financetracker.application.domain.service.budget;

import website.ylab.financetracker.application.domain.model.budget.BudgetModel;
import website.ylab.financetracker.application.port.out.budget.ExternalBudgetStorage;

import java.util.Optional;

public class BudgetDAO {
    private final ExternalBudgetStorage storage;

    public BudgetDAO(ExternalBudgetStorage storage) {
        this.storage = storage;
    }

    /**
     * Budget creation operation
     * @param budget Budget for creation
     * @return Optional<BudgetModel> with a new budget or empty Optional if the operation failed
     */
    public Optional<BudgetModel> create(BudgetModel budget) {
        return storage.create(budget);
    }

    /**
     * Get budget by id
     * @param id long budget id
     * @return Optional<BudgetModel> if successful or empty Optional if no budget with that id was found
     */
    public Optional<BudgetModel> getById(long id) {
        return storage.getById(id);
    }

    /**
     * Delete budget by id.
     * @param id long budget id
     * @return Optional<BudgetModel> if successful
     *      * or empty Optional if no budget with that ID was found to delete.
     */
    public Optional<BudgetModel> delete(long id){
        return storage.delete(id);
    }
}