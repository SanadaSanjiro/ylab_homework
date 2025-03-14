package website.ylab.financetracker.adapter.out.persistence.transaction;

import website.ylab.financetracker.application.port.out.transaction.ExternalTransactionStorage;

public class TransactionStorageFactory {
    private static final ExternalTransactionStorage storage = createStorage();

    public static ExternalTransactionStorage getStorage() {
        return storage;
    }


    public static ExternalTransactionStorage createStorage() {
        TransactionEntityMapper mapper = new TransactionEntityMapper();
        TransactionRepository repository = new TransactionRepositoryImplementation();
        return new TransactionPersistenceAdapter(repository, mapper);
    }
}
