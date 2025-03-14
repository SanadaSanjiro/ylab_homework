package website.ylab.financetracker.adapter.out.persistence.target;

import java.util.HashMap;
import java.util.Map;

public class TargetRepositoryImplementation implements TargetRepository {
    private final Map<Long, TargetEntity> storage = new HashMap<>();

    @Override
    public TargetEntity create(TargetEntity target) {
        storage.put(target.getUserId(), target);
        return target;
    }

    @Override
    public TargetEntity getById(long id) {
        return storage.get(id);
    }

    @Override
    public TargetEntity delete(long id) {
        TargetEntity target = storage.get(id);
        storage.remove(id);
        return target;
    }
}
