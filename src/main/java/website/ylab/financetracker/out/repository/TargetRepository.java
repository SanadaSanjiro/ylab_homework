package website.ylab.financetracker.out.repository;

import website.ylab.financetracker.service.targets.TrackerTarget;

import java.util.Optional;

public interface TargetRepository {
    Optional<TrackerTarget> setTarget(TrackerTarget target);
    Optional<TrackerTarget> getById(long id);
    Optional<TrackerTarget> getByUserId(long userId);
    Optional<TrackerTarget> deleteTarget(long id);
}
