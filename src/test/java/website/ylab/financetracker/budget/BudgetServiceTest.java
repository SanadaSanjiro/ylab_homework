package website.ylab.financetracker.budget;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import website.ylab.financetracker.auth.TrackerUser;
import website.ylab.financetracker.out.persistence.BudgetRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BudgetServiceTest {
    BudgetRepository repository = Mockito.mock(BudgetRepository.class);
    BudgetService budgetService = new BudgetService(repository);
    TrackerUser user = new TrackerUser();
    Double limit = 100.0;

    @Test
    void setBudget() {
        Mockito.when(repository.setBudget(user, limit)).thenReturn(Optional.of(limit));
        assertEquals("Budget set to 100.0", budgetService.setBudget(user, limit));
    }

    @Test
    void getBudget() {
        Mockito.when(repository.getBudget(user)).thenReturn(Optional.of(limit));
        assertEquals(limit, budgetService.getBudget(user));
    }

    @Test
    void deleteBudget() {
        Mockito.when(repository.deleteBudget(user)).thenReturn(Optional.of(limit));
        assertEquals("Budget successfully deleted", budgetService.deleteBudget(user));
    }
}