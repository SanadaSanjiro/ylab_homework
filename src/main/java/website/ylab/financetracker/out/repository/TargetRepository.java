package website.ylab.financetracker.out.repository;

import website.ylab.financetracker.service.targets.TrackerTarget;

import java.util.Optional;

/**
 * Describes methods for working with target entities
 */
public interface TargetRepository {
    /**
     * Sets a new target for a user. If the user already had a target, the old one must be deleted beforehand
     * @param target TrackerTarget new users target
     * @return Optional<TrackerTarget> on success or an empty Optional on failure
     */
    Optional<TrackerTarget> setTarget(TrackerTarget target);

    /**
     * Gets target value by its ID
     * @param id long target ID
     * @return Optional<TrackerTarget> on success, or an empty Optional if nothing was found
     */
    Optional<TrackerTarget> getById(long id);

    /**
     * Gets the target value by its owner ID
     * @param userId long user ID
     * @return Optional<TrackerTarget> on success, or an empty Optional if nothing was found
     */
    Optional<TrackerTarget> getByUserId(long userId);

    /**
     * Deletes target from a storage
     * @param id long target ID
     * @return Optional<TrackerTarget> on success or an empty Optional on failure
     */
    Optional<TrackerTarget> deleteTarget(long id);
}
