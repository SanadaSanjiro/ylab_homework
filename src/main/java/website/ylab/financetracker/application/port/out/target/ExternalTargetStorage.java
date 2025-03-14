package website.ylab.financetracker.application.port.out.target;

import website.ylab.financetracker.application.domain.model.target.TargetModel;

import java.util.Optional;

/**
 * Operations with targets
 */
public interface ExternalTargetStorage {
    /**
     * Create targets operation
     * @param target TargetModel for creation
     * @return Optional<BudgetModel> with a new target or empty Optional if the operation failed
     */
    Optional<TargetModel> create(TargetModel target);

    /**
     * Get target by id
     * @param id long target id, must be equal id of user, that created this target
     * @return Optional<TargetModel> if successful or empty Optional if no target with that id was found
     */
    Optional<TargetModel> getById(long id);

    /**
     * Deleting a target
     * @param id long target's number
     * @return Optional<TargetModel> if successful
     *      *  or empty Optional if no target with that ID was found to delete.
     */
    Optional<TargetModel>delete(long id);
}