package website.ylab.financetracker.controllers.transaction;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import website.ylab.financetracker.in.dto.transaction.ChangeTransactionDTO;
import website.ylab.financetracker.in.dto.transaction.FilterDTO;
import website.ylab.financetracker.in.dto.transaction.TransactionRequest;
import website.ylab.financetracker.in.dto.transaction.TransactionResponse;
import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.service.transactions.TrackerTransaction;
import website.ylab.financetracker.service.transactions.TransactionFilter;
import website.ylab.financetracker.service.transactions.TransactionService;
import website.ylab.financetracker.service.transactions.TransactionType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionControllerTest {
    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    TransactionType type1 = TransactionType.INCOME;
    String category1 = "Travel";
    String username = "Bob";
    String password = "123456";
    String email = "bob@gmail.com";
    TrackerUser user;
    TransactionService transactionService = Mockito.mock(TransactionService.class);
    HttpSession session = Mockito.mock(HttpSession.class);
    TransactionController controller = new TransactionController(transactionService);

    @BeforeEach
    void setUp() {
        user=new TrackerUser()
                .setUsername(username)
                .setEmail(email)
                .setPassword(password)
                .setId(1L);
    }

    @Test
    void getById() {
        Mockito.when(session.getAttribute("userid")).thenReturn("1");
        TransactionResponse transactionResponse = getResponse(type1, category1);
        Mockito.when(transactionService.getById(Mockito.anyLong())).thenReturn(transactionResponse);

        ResponseEntity<TransactionResponse> result = controller.getById(1L, session);
        assertEquals("200 OK", result.getStatusCode().toString());
        assertEquals(100.0, result.getBody().getAmount());
        assertEquals("Travel", result.getBody().getCategory());
    }

    @Test
    void getFiltered() {
        Mockito.when(session.getAttribute("userid")).thenReturn("1");
        TransactionResponse transactionResponse = getResponse(type1, category1);
        Mockito.when(transactionService.getFiltered(Mockito.any())).thenReturn(List.of(transactionResponse));

        FilterDTO dto = new FilterDTO();
        ResponseEntity<List<TransactionResponse>> result = controller.getFiltered(
                dto, session);
        assertEquals("200 OK", result.getStatusCode().toString());
        assertEquals(100.0, result.getBody().get(0).getAmount());
        assertEquals("Travel", result.getBody().get(0).getCategory());
    }

    @Test
    void deleteTransaction() {
        Mockito.when(session.getAttribute("userid")).thenReturn("1");
        TransactionResponse transactionResponse = getResponse(type1, category1);
        transactionResponse.setUserId(1L);
        Mockito.when(transactionService.getById(Mockito.anyLong())).thenReturn(transactionResponse);
        Mockito.when(transactionService.deleteTransaction(Mockito.anyLong())).thenReturn(transactionResponse);

        ResponseEntity<TransactionResponse> result = controller.deleteTransaction( 1L, session);
        assertEquals("200 OK", result.getStatusCode().toString());
        assertEquals(100.0, result.getBody().getAmount());
        assertEquals("Travel", result.getBody().getCategory());
    }

    @Test
    void changeTransaction() {
        Mockito.when(session.getAttribute("userid")).thenReturn("1");
        TransactionResponse transactionResponse = getResponse(type1, category1);
        Mockito.when(transactionService.changeTransaction(Mockito.any())).thenReturn(transactionResponse);
        ChangeTransactionDTO dto = new ChangeTransactionDTO().setAmount(100.0)
                .setDescription("new desc")
                .setCategory("Travel");
        ResponseEntity<TransactionResponse> result = controller.changeTransaction(dto, session);
        assertEquals("200 OK", result.getStatusCode().toString());
        assertEquals(100.0, result.getBody().getAmount());
        assertEquals("Travel", result.getBody().getCategory());
    }

    @Test
    void addTransaction() {
        Mockito.when(session.getAttribute("userid")).thenReturn("1");

        TransactionResponse transactionResponse = getResponse(type1, category1);
        Mockito.when(transactionService.addNewTransaction(Mockito.any())).thenReturn(transactionResponse);

        TransactionRequest dto = getRequest();
        ResponseEntity<TransactionResponse> result = controller.addTransaction(dto, session);
        assertEquals("200 OK", result.getStatusCode().toString());
        assertEquals(100.0, result.getBody().getAmount());
    }

    private TransactionResponse getResponse(TransactionType type, String category) {
        TransactionResponse response = new TransactionResponse();
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

    private TransactionRequest getRequest() {
        TransactionRequest dto = new TransactionRequest()
                .setType(type1.toString())
                .setAmount(100.0)
                .setCategory(category1);
        try {
            dto.setDate(formatter.parse(1 + ".01.2025"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dto;
    }
}