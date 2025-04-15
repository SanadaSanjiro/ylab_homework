package website.ylab.financetracker.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.service.auth.Role;
import website.ylab.financetracker.service.auth.UserService;

import static org.mockito.Mockito.times;

class AdminCreatorTest {
    UserService service = Mockito.mock(UserService.class);
    AdminCreator creator = new AdminCreator(service);

    @Test
    void createAdmin() {
        Mockito.when(service.getByName(Mockito.anyString())).thenReturn(null);
        Mockito.when(service.addNewUser(Mockito.any())).thenReturn(new UserResponse().setId(1L));
        creator.createAdmin();
        Mockito.verify(service, times(1)).getByName(Mockito.anyString());
        Mockito.verify(service, times(1)).addNewUser(Mockito.any());
        Mockito.verify(service, times(1)).changeUserRole(1L, Role.ADMIN);
    }
}