package website.ylab.financetracker.out.persistence;

import website.ylab.financetracker.out.persistence.ram.transaction.RamTransactionRepo;

/**
 * Provides an implementation of a transaction repository
 */

public class TransactionRepositoryProvider {
    private final static TrackerTransactionRepository repository = new RamTransactionRepo();

    /**
     * Provides an implementation of a transaction repository
     * @return repository singleton
     */
    public static TrackerTransactionRepository getRepository() {
        return repository;
    }
}
