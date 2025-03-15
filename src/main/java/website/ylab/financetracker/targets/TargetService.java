package website.ylab.financetracker.targets;

import website.ylab.financetracker.auth.TrackerUser;
import website.ylab.financetracker.out.persistence.TargetRepository;

import java.util.Optional;

public class TargetService {
    private final TargetRepository targetRepository;

    public TargetService(TargetRepository targetRepository) {
        this.targetRepository = targetRepository;
    }

    public String setTarget(TrackerUser user, Double target) {
        Optional<Double> optional = targetRepository.setTarget(user, target);
        return optional.map(aDouble -> "Target set to " + aDouble).orElse("Target not set");
    }

    public Double getTarget(TrackerUser user) {
        Optional<Double> optional = targetRepository.getTarget(user);
        return optional.orElse(null);
    }

    public String deleteTarget(TrackerUser user) {
        Optional<Double> optional = targetRepository.deleteTarget(user);
        return optional.map(aDouble -> "Target successfully deleted").orElse("Error deleting target");
    }
}
