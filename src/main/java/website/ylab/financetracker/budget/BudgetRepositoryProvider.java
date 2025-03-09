package website.ylab.financetracker.budget;

/**
 * Provides an implementation of a budget repository
 */
public class BudgetRepositoryProvider {
    private static final BudgetRepository repository = new RamBudgetRepo();

    /**
     * Provides an implementation of a budget repository
     * @return repository singleton
     */
    public static BudgetRepository getRepository() {
        return repository;
    }
}
