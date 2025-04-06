package website.ylab.financetracker.controllers.adm;

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
import website.ylab.financetracker.in.dto.auth.RoleDTO;
import website.ylab.financetracker.in.dto.auth.UserIdDTO;
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.in.dto.transaction.TransactionResponse;
import website.ylab.financetracker.service.AdminCreator;
import website.ylab.financetracker.service.auth.Role;
import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.service.auth.UserService;
import website.ylab.financetracker.service.transactions.TransactionService;
import website.ylab.financetracker.service.transactions.TransactionType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdmController.class)
class AdmControllerTest {
    @Autowired
    MockMvc mvc;

    String username = "Bob";
    String password = "123456";
    String email = "bob@gmail.com";
    TrackerUser user;
    UserResponse response;
    HashMap<String, Object> sessionattr;

    @MockBean
    UserService userService;
    @MockBean
    TransactionService transactionService;
    @MockBean
    AdminCreator adminCreator;

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
        sessionattr = new HashMap<String, Object>();
        sessionattr.put("role", Role.ADMIN.toString());
    }

    @Test
    void blockUser() throws Exception {
        Mockito.when(userService.blockUser(Mockito.anyLong())).thenReturn(response.setEnabled(false));
        UserIdDTO dto = new UserIdDTO().setUserid(1L);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(dto);
        MvcResult result = mvc.perform(put("/adm/block")
                        .sessionAttrs(sessionattr)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String json = result.getResponse().getContentAsString();
        UserResponse userResponse = mapper.readValue(json, UserResponse.class);
        assertEquals(username, userResponse.getName());
        assertFalse(userResponse.isEnabled());
    }

    @Test
    void unblockUser() throws Exception {
        Mockito.when(userService.unblockUser(Mockito.anyLong())).thenReturn(response.setEnabled(true));
        UserIdDTO dto = new UserIdDTO().setUserid(1L);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(dto);
        MvcResult result = mvc.perform(put("/adm/unblock")
                        .sessionAttrs(sessionattr)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String json = result.getResponse().getContentAsString();
        UserResponse userResponse = mapper.readValue(json, UserResponse.class);
        assertEquals(username, userResponse.getName());
        assertTrue(userResponse.isEnabled());
    }

    @Test
    void changeRole() throws Exception {
        Mockito.when(userService.changeUserRole(Mockito.anyLong(), Mockito.any()))
                .thenReturn(response.setRole(Role.ADMIN.toString()));
        RoleDTO dto = new RoleDTO().setUserid(1L).setRole("USER");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(dto);
        MvcResult result = mvc.perform(put("/adm/role")
                        .sessionAttrs(sessionattr)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String json = result.getResponse().getContentAsString();
        UserResponse userResponse = mapper.readValue(json, UserResponse.class);
        assertEquals(username, userResponse.getName());
        assertEquals(Role.ADMIN.toString(), userResponse.getRole());
    }

    @Test
    void deleteUser() throws Exception {
        Mockito.when(userService.deleteUser(Mockito.anyLong()))
                .thenReturn(response);
        MvcResult result = mvc.perform(delete("/adm/delete/1")
                        .sessionAttrs(sessionattr))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        UserResponse userResponse = mapper.readValue(json, UserResponse.class);
        assertEquals(username, userResponse.getName());
    }

    @Test
    void getUsers() throws Exception {
        Mockito.when(userService.getAllUsersResponse()).thenReturn(List.of(response));
        MvcResult result = mvc.perform(get("/adm/users")
                        .sessionAttrs(sessionattr))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        List<UserResponse> actual = mapper.readValue(json, new TypeReference<List<UserResponse>>() {});
        assertEquals(username, actual.get(0).getName());
    }

    @Test
    void getUserTransactions() throws Exception {
        TransactionType type1 = TransactionType.INCOME;
        String category1 = "Travel";
        TransactionResponse transactionResponse = getResponse(type1, category1);
        Mockito.when(transactionService.getUserTransaction(Mockito.anyLong()))
                .thenReturn(List.of(transactionResponse));
        MvcResult result = mvc.perform(get("/adm/transactions/1")
                        .sessionAttrs(sessionattr))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        List<TransactionResponse> actual = mapper.readValue(json, new TypeReference<List<TransactionResponse>>() {});
        assertEquals(100.0, actual.get(0).getAmount());
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