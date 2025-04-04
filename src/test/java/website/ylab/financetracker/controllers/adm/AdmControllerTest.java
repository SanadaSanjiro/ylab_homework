package website.ylab.financetracker.controllers.adm;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import website.ylab.financetracker.in.dto.auth.RoleDTO;
import website.ylab.financetracker.in.dto.auth.UserIdDTO;
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.in.dto.transaction.TransactionResponse;
import website.ylab.financetracker.service.auth.Role;
import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.service.auth.UserService;
import website.ylab.financetracker.service.transactions.TransactionService;
import website.ylab.financetracker.service.transactions.TransactionType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdmControllerTest {
    String username = "Bob";
    String password = "123456";
    String email = "bob@gmail.com";
    TrackerUser user;
    UserResponse response;
    UserService userService = Mockito.mock(UserService.class);
    TransactionService transactionService = Mockito.mock(TransactionService.class);
    AdmController controller = new AdmController(userService, transactionService);
    HttpSession session = Mockito.mock(HttpSession.class);

    @BeforeEach
    void setUp() {
        user=new TrackerUser()
                .setUsername(username)
                .setEmail(email)
                .setPassword(password)
                .setId(1L);
        response = new UserResponse()
                .setId(1L)
                .setName(username)
                .setEmail(email)
                .setRole(Role.USER.toString())
                .setEnabled(true);
    }

    @Test
    void blockUser() {
        Mockito.when(session.getAttribute("role")).thenReturn(Role.ADMIN.toString());
        Mockito.when(userService.blockUser(Mockito.anyLong())).thenReturn(response.setEnabled(false));

        UserIdDTO dto = new UserIdDTO().setUserid(1L);
        ResponseEntity<UserResponse> result = controller.blockUser(dto, session);
        assertEquals("200 OK", result.getStatusCode().toString());
        assertEquals(username, result.getBody().getName());
        assertFalse(result.getBody().isEnabled());
    }

    @Test
    void unblockUser() {
        Mockito.when(session.getAttribute("role")).thenReturn(Role.ADMIN.toString());
        Mockito.when(userService.unblockUser(Mockito.anyLong())).thenReturn(response.setEnabled(true));

        UserIdDTO dto = new UserIdDTO().setUserid(1L);
        ResponseEntity<UserResponse> result = controller.unblockUser(dto, session);
        assertEquals("200 OK", result.getStatusCode().toString());
        assertEquals(username, result.getBody().getName());
        assertTrue(result.getBody().isEnabled());
    }

    @Test
    void changeRole() {
        Mockito.when(session.getAttribute("role")).thenReturn(Role.ADMIN.toString());
        Mockito.when(userService.changeUserRole(Mockito.anyLong(), Mockito.any()))
                .thenReturn(response.setRole(Role.ADMIN.toString()));

        RoleDTO dto = new RoleDTO().setUserid(1L).setRole("USER");
        ResponseEntity<UserResponse> result = controller.changeRole(dto, session);
        assertEquals("200 OK", result.getStatusCode().toString());
        assertEquals(username, result.getBody().getName());
        assertEquals(Role.ADMIN.toString(), result.getBody().getRole());
    }

    @Test
    void deleteUser() {
        Mockito.when(session.getAttribute("role")).thenReturn(Role.ADMIN.toString());
        Mockito.when(userService.deleteUser(Mockito.anyLong()))
                .thenReturn(response);

        ResponseEntity<UserResponse> result = controller.deleteUser(1L, session);
        assertEquals("200 OK", result.getStatusCode().toString());
        assertEquals(username, result.getBody().getName());
    }

    @Test
    void getUsers() {
        Mockito.when(session.getAttribute("role")).thenReturn(Role.ADMIN.toString());
        Mockito.when(userService.getAllUsersResponse()).thenReturn(List.of(response));

        ResponseEntity<List<UserResponse>> result = controller.getUsers(session);
        assertEquals("200 OK", result.getStatusCode().toString());
        assertEquals(username, result.getBody().get(0).getName());
    }

    @Test
    void getUserTransactions() {
        TransactionType type1 = TransactionType.INCOME;
        String category1 = "Travel";
        TransactionResponse transactionResponse = getResponse(type1, category1);
        Mockito.when(session.getAttribute("role")).thenReturn(Role.ADMIN.toString());
        Mockito.when(transactionService.getUserTransaction(Mockito.anyLong()))
                .thenReturn(List.of(transactionResponse));
        ResponseEntity<List<TransactionResponse>> result =controller.getUserTransactions(1L, session);
        assertEquals("200 OK", result.getStatusCode().toString());
        assertEquals(100.0, result.getBody().get(0).getAmount());
    }

    private TransactionResponse getResponse(TransactionType type, String category) {
        TransactionResponse response = new TransactionResponse();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        response.setId(1L)
                .setType(type.toString())
                .setAmount(100.0)
                .setCategory(category)
                .setId(user.getId());
        try {
            response.setDate(formatter.parse(1 + ".01.2025"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return response;
    }
}