package website.ylab.financetracker.service.budget;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import website.ylab.financetracker.in.dto.budget.BudgetResponse;
import website.ylab.financetracker.out.repository.BudgetRepository;
import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.service.auth.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BudgetServiceTest {
    long id = 1L;
    long userId = 1L;
    double limit = 100.0;
    UserService userService = Mockito.mock(UserService.class);
    BudgetRepository repository = Mockito.mock(BudgetRepository.class);
    BudgetService service = new BudgetService(repository, userService);

    @Test
    void setBudget() {
        Mockito.when(userService.getById(Mockito.anyLong())).thenReturn(Optional.of(new TrackerUser()));
        Mockito.when(repository.setBudget(Mockito.any())).thenReturn(Optional.of(testBudget()));
        BudgetResponse response = service.setBudget(testBudget());
        assertEquals(id, response.getId());
        assertEquals(userId, response.getUserId());
        assertEquals(limit, response.getLimit());
    }

    @Test
    void getBudget() {
        Mockito.when(repository.getById(Mockito.anyLong())).thenReturn(Optional.of(testBudget()));
        BudgetResponse response = service.getBudget(id);
        assertEquals(id, response.getId());
        assertEquals(userId, response.getUserId());
        assertEquals(limit, response.getLimit());
    }

    @Test
    void getByUserId() {
        Mockito.when(repository.getByUserId(Mockito.anyLong())).thenReturn(Optional.of(testBudget()));
        BudgetResponse response = service.getByUserId(userId);
        assertEquals(id, response.getId());
        assertEquals(userId, response.getUserId());
        assertEquals(limit, response.getLimit());
    }

    private TrackerBudget testBudget() {
        return new TrackerBudget().setId(id).setUserId(userId).setLimit(limit);
    }
}