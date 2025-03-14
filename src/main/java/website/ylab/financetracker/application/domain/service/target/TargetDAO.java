package website.ylab.financetracker.application.domain.service.target;

import website.ylab.financetracker.application.domain.model.target.TargetModel;
import website.ylab.financetracker.application.port.out.target.ExternalTargetStorage;

import java.util.Optional;

public class TargetDAO {
    private final ExternalTargetStorage storage;

    public TargetDAO(ExternalTargetStorage storage) {
        this.storage = storage;
    }

    /**
     * Target creation operation
     * @param target Target for creation
     * @return Optional<TargetModel> with a new target or empty Optional if the operation failed
     */
    public Optional<TargetModel> create(TargetModel target) {
        return storage.create(target);
    }

    /**
     * Get target by id
     * @param id long target id
     * @return Optional<TargetModel> if successful or empty Optional if no target with that id was found
     */
    public Optional<TargetModel> getById(long id) {
        return storage.getById(id);
    }

    /**
     * Delete target by id.
     * @param id long target id
     * @return Optional<TargetModel> if successful
     *      * or empty Optional if no target with that ID was found to delete.
     */
    public Optional<TargetModel> delete(long id){
        return storage.delete(id);
    }
}