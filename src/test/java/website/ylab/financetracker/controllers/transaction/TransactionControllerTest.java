package website.ylab.financetracker.controllers.transaction;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import website.ylab.financetracker.in.dto.transaction.ChangeTransactionDTO;
import website.ylab.financetracker.in.dto.transaction.FilterDTO;
import website.ylab.financetracker.in.dto.transaction.TransactionRequest;
import website.ylab.financetracker.in.dto.transaction.TransactionResponse;
import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.service.transactions.TransactionService;
import website.ylab.financetracker.service.transactions.TransactionType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class TransactionControllerTest {
    @Autowired
    MockMvc mvc;

    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    TransactionType type1 = TransactionType.INCOME;
    String category1 = "Travel";
    String username = "Bob";
    String password = "123456";
    String email = "bob@gmail.com";
    TrackerUser user;

    @MockBean
    TransactionService transactionService;

    @BeforeEach
    void setUp() {
        user=new TrackerUser()
                .setUsername(username)
                .setEmail(email)
                .setPassword(password)
                .setId(1L);
    }

    @Test
    void getById() throws Exception {
        TransactionResponse transactionResponse = getResponse(type1, category1);
        Mockito.when(transactionService.getById(Mockito.anyLong())).thenReturn(transactionResponse);
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("userid", "1");

        MvcResult result = mvc.perform(get("/transaction/get/1").sessionAttrs(sessionattr))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        TransactionResponse response = mapper.readValue(json, TransactionResponse.class);
        assertEquals(100.0, response.getAmount());
        assertEquals("Travel", response.getCategory());
    }

    @Test
    void getFiltered() throws Exception {
        TransactionResponse transactionResponse = getResponse(type1, category1);
        Mockito.when(transactionService.getFiltered(Mockito.any())).thenReturn(List.of(transactionResponse));
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("userid", "1");

        FilterDTO dto = new FilterDTO();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(dto);
        MvcResult result = mvc.perform(post("/transaction/filtered").sessionAttrs(sessionattr)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String json = result.getResponse().getContentAsString();
        List<TransactionResponse> actual = mapper.readValue(json, new TypeReference<List<TransactionResponse>>() {});
        assertEquals(100.0, actual.get(0).getAmount());
        assertEquals("Travel", actual.get(0).getCategory());
    }

    @Test
    void deleteTransaction() throws Exception {
        TransactionResponse transactionResponse = getResponse(type1, category1);
        transactionResponse.setUserId(1L);
        Mockito.when(transactionService.getById(Mockito.anyLong())).thenReturn(transactionResponse);
        Mockito.when(transactionService.deleteTransaction(Mockito.anyLong())).thenReturn(transactionResponse);
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("userid", "1");

        MvcResult result = mvc.perform(delete("/transaction/delete/1").sessionAttrs(sessionattr))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        TransactionResponse response = mapper.readValue(json, TransactionResponse.class);
        assertEquals(100.0, response.getAmount());
        assertEquals("Travel", response.getCategory());
    }

    @Test
    void changeTransaction() throws Exception {
        TransactionResponse transactionResponse = getResponse(type1, category1);
        Mockito.when(transactionService.changeTransaction(Mockito.any())).thenReturn(transactionResponse);
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("userid", "1");
        ChangeTransactionDTO dto = new ChangeTransactionDTO().setAmount(100.0)
                .setDescription("new desc")
                .setCategory("Travel");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(dto);
        MvcResult result = mvc.perform(put("/transaction/change").sessionAttrs(sessionattr)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String json = result.getResponse().getContentAsString();
        TransactionResponse response = mapper.readValue(json, TransactionResponse.class);
        assertEquals(100.0, response.getAmount());
        assertEquals("Travel", response.getCategory());
    }

    @Test
    void addTransaction() throws Exception {
        TransactionResponse transactionResponse = getResponse(type1, category1);
        Mockito.when(transactionService.addNewTransaction(Mockito.any())).thenReturn(transactionResponse);
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("userid", "1");
        TransactionRequest dto = getRequest();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(dto);

        MvcResult result = mvc.perform(post("/transaction/add").sessionAttrs(sessionattr)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        TransactionResponse response = mapper.readValue(json, TransactionResponse.class);
        assertEquals(100.0, response.getAmount());
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