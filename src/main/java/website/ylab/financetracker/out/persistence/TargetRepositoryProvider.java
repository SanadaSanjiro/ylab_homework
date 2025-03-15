package website.ylab.financetracker.out.persistence;

import website.ylab.financetracker.out.persistence.ram.target.RamTargetRepo;

/**
 * Provides an implementation of a target repository
 */
public class TargetRepositoryProvider {
    private static final TargetRepository repository = new RamTargetRepo();

    /**
     * Provides an implementation of a target repository
     * @return repository singleton
     */
    public static TargetRepository getRepository() {
        return repository;
    }
}
