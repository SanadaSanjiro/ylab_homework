package website.ylab.financetracker.in.dto.target;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.in.mappers.TargetMapper;
import website.ylab.financetracker.service.targets.TrackerTarget;

import static org.junit.jupiter.api.Assertions.*;
class TargetMapperTest {
    long id = 1L;
    long userId = 1L;
    double amount = 100.0;
    String uuid = "uuid";

    @Test
    void toResponse() {
        TargetMapper mapper = TargetMapper.INSTANCE;
        TrackerTarget target = new TrackerTarget().setId(id).setAmount(amount).setUserId(userId).setUuid(uuid);
        TargetResponse response = mapper.toResponse(target);
        assertEquals(id, response.getId());
        assertEquals(userId, response.getUserId());
        assertEquals(amount, response.getAmount());
        assertEquals(uuid, response.getUuid());
    }

    @Test
    void toTarget() {
        TargetMapper mapper = TargetMapper.INSTANCE;
        TargetResponse response = new TargetResponse().setId(id).setUserId(userId).setAmount(amount).setUuid(uuid);
        TrackerTarget target = mapper.toTarget(response);
        assertEquals(id, target.getId());
        assertEquals(userId, target.getUserId());
        assertEquals(amount, target.getAmount());
        assertEquals(uuid, target.getUuid());
    }
}