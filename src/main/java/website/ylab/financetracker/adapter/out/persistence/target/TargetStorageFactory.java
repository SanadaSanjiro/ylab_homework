package website.ylab.financetracker.adapter.out.persistence.target;

import website.ylab.financetracker.application.port.out.target.ExternalTargetStorage;

public class TargetStorageFactory {
    private static ExternalTargetStorage storage = createStorage();

    public static ExternalTargetStorage getStorage() {
        return storage;
    }

    private static ExternalTargetStorage createStorage() {
        TargetEntityMapper mapper = new TargetEntityMapper();
        TargetRepository repository = new TargetRepositoryImplementation();
        return new TargetPersistenceAdapter(repository, mapper);
    }
}