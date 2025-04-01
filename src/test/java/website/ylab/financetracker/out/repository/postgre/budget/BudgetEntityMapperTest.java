package website.ylab.financetracker.out.repository.postgre.budget;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.service.budget.TrackerBudget;

import static org.junit.jupiter.api.Assertions.*;

class BudgetEntityMapperTest {
    long id = 1L;
    long userId = 1L;
    double limit = 100.0;
    String uuid = "uuid";

    @Test
    void toEntity() {
        BudgetEntityMapper mapper = BudgetEntityMapper.INSTANCE;
        TrackerBudget budget = new TrackerBudget().setId(id).setLimit(limit).setUserId(userId).setUuid(uuid);
        BudgetEntity response = mapper.toEntity(budget);
        assertEquals(id, response.getId());
        assertEquals(userId, response.getUserId());
        assertEquals(limit, response.getLimit());
        assertEquals(uuid, response.getUuid());

    }

    @Test
    void toBudget() {
        BudgetEntityMapper mapper = BudgetEntityMapper.INSTANCE;
        BudgetEntity entity = new BudgetEntity().setId(id).setUserId(userId).setLimit(limit).setUuid(uuid);
        TrackerBudget budget = mapper.toBudget(entity);
        assertEquals(id, budget.getId());
        assertEquals(userId, budget.getUserId());
        assertEquals(limit, budget.getLimit());
        assertEquals(uuid, budget.getUuid());
    }
}