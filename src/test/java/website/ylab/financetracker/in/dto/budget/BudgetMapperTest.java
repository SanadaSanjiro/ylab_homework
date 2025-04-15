package website.ylab.financetracker.in.dto.budget;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.in.mappers.BudgetMapper;
import website.ylab.financetracker.service.budget.TrackerBudget;

import static org.junit.jupiter.api.Assertions.*;

class BudgetMapperTest {
    long id = 1L;
    long userId = 1L;
    double limit = 100.0;
    String uuid = "uuid";

    @Test
    void toResponse() {
        BudgetMapper mapper = BudgetMapper.INSTANCE;
        TrackerBudget target = new TrackerBudget().setId(id).setLimit(limit).setUserId(userId).setUuid(uuid);
        BudgetResponse response = mapper.toResponse(target);
        assertEquals(id, response.getId());
        assertEquals(userId, response.getUserId());
        assertEquals(limit, response.getLimit());
        assertEquals(uuid, response.getUuid());
    }

    @Test
    void toTarget() {
        BudgetMapper mapper = BudgetMapper.INSTANCE;
        BudgetResponse response = new BudgetResponse().setId(id).setUserId(userId).setLimit(limit).setUuid(uuid);
        TrackerBudget target = mapper.toTarget(response);
        assertEquals(id, target.getId());
        assertEquals(userId, target.getUserId());
        assertEquals(limit, target.getLimit());
        assertEquals(uuid, target.getUuid());
    }
}