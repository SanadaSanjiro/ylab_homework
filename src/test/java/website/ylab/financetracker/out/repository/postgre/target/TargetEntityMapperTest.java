package website.ylab.financetracker.out.repository.postgre.target;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.service.targets.TrackerTarget;

import static org.junit.jupiter.api.Assertions.*;

class TargetEntityMapperTest {
    long id = 1L;
    long userId = 1L;
    double amount = 100.0;
    String uuid = "uuid";

    @Test
    void toEntity() {
        TargetEntityMapper mapper = TargetEntityMapper.INSTANCE;
        TrackerTarget target = new TrackerTarget().setId(id).setAmount(amount).setUserId(userId).setUuid(uuid);
        TargetEntity response = mapper.toEntity(target);
        assertEquals(id, response.getId());
        assertEquals(userId, response.getUserId());
        assertEquals(amount, response.getAmount());
        assertEquals(uuid, response.getUuid());
    }

    @Test
    void toTarget() {
        TargetEntityMapper mapper = TargetEntityMapper.INSTANCE;
        TargetEntity entity = new TargetEntity().setId(id).setUserId(userId).setAmount(amount).setUuid(uuid);
        TrackerTarget target = mapper.toTarget(entity);
        assertEquals(id, target.getId());
        assertEquals(userId, target.getUserId());
        assertEquals(amount, target.getAmount());
        assertEquals(uuid, target.getUuid());
    }
}