package website.ylab.financetracker.api;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import website.ylab.financetracker.ServiceProvider;
import website.ylab.financetracker.auth.TrackerUser;
import website.ylab.financetracker.auth.UserService;
import website.ylab.financetracker.budget.BudgetDataInput;
import website.ylab.financetracker.budget.BudgetService;
import website.ylab.financetracker.in.dto.auth.UserResponse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApiServiceTest {
    BudgetDataInput budgetDataInput = Mockito.mock(BudgetDataInput.class);
    TrackerUser user = new TrackerUser("Bob", "bob@gmail.com", "123");

    @Test
    void isExceeded() {
        Mockito.when(budgetDataInput.isExceeded(Mockito.any())).thenReturn(true);
        ApiService apiService = new ApiService(budgetDataInput);
        assertTrue(apiService.isExceeded(user));
    }

    @Test
    void getEmailNotifications() {
        UserService userService = Mockito.mock(UserService.class);
        BudgetService budgetService = Mockito.mock(BudgetService.class);
        Mockito.when(userService.getAllUsersResponse()).thenReturn(List.of(createResponse()));
        Mockito.when(budgetService.getBudget(Mockito.any())).thenReturn(Double.valueOf(100));
        Mockito.when(budgetDataInput.isExceeded(Mockito.any())).thenReturn(true);
        try (MockedStatic<ServiceProvider> serviceProvider = Mockito.mockStatic(ServiceProvider.class)) {
            serviceProvider.when(ServiceProvider::getUserService).thenReturn(userService);
            serviceProvider.when(ServiceProvider::getBudgetService).thenReturn(budgetService);
            ApiService apiService = new ApiService(budgetDataInput);
            assertFalse(apiService.getEmailNotifications().isEmpty());
        }
    }
    private TrackerUser createUser() {
        String username = "Bob";
        String password = "123456";
        String email = "bob@gmail.com";
        TrackerUser user=new TrackerUser(username, email, password);
        return user;
    }

    private UserResponse createResponse() {
        String username = "Bob";
        String password = "123456";
        String email = "bob@gmail.com";
        UserResponse response = new UserResponse().setName(username).setEmail(email);
        return response;
    }
}