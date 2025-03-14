package website.ylab.financetracker.adapter.out.persistence.budget;

import website.ylab.financetracker.application.port.out.budget.ExternalBudgetStorage;

public class BudgetStorageFactory {
    private static final ExternalBudgetStorage storage = getBudgetStorage();

    public static ExternalBudgetStorage getStorage() {
        return storage;
    }

    private static ExternalBudgetStorage getBudgetStorage() {
        BudgetEntityMapper mapper = new BudgetEntityMapper();
        BudgetRepository repository = new BudgetRepositoryImplementation();
        return new BudgetPersistenceAdapter(repository, mapper);
    }
}