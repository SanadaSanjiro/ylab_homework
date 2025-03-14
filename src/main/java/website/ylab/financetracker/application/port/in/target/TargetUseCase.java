package website.ylab.financetracker.application.port.in.target;

import website.ylab.financetracker.application.domain.model.target.TargetModel;

public interface TargetUseCase {
    /**
     * Target creation operation
     * @param target Target for creation
     * @return Optional<TargetModel> with a new target or empty Optional if the operation failed
     */
    TargetModel create(TargetModel target);

    /**
     * Get target of current user
     * @return Optional<TargetModel> if successful or empty Optional if no target with that id was found
     */
    TargetModel get();

    /**
     * Delete target of current user
     * @return Optional<TargetModel> if successful
     * or empty Optional if no target with that ID was found to delete.
     */
    TargetModel delete();

    /**
     * Checks if the current user's target has been reached.
     * @return true if the sum of INCOME transactions in the last month is greater than the target
     */
    boolean isReached();
}