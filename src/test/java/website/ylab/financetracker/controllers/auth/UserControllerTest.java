package website.ylab.financetracker.controllers.auth;

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
import website.ylab.financetracker.in.dto.auth.UserDataDTO;
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.service.AdminCreator;
import website.ylab.financetracker.service.auth.Role;
import website.ylab.financetracker.service.auth.UserDataVerificator;
import website.ylab.financetracker.service.auth.UserService;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    MockMvc mvc;

    String username = "Bob";
    String newUsername = "Alice";
    String password = "123456";
    String email = "bob@gmail.com";
    UserResponse response;
    @MockBean
    UserService userService;
    @MockBean
    UserDataVerificator verifier;
    @MockBean
    AdminCreator adminCreator;

    @BeforeEach
    void setUp() {
        response = new UserResponse()
                .setId(1L)
                .setName(username)
                .setEmail(email)
                .setRole(Role.USER.toString())
                .setEnabled(true);
    }

    @Test
    void addUser() throws Exception {
        Mockito.when(verifier.isValidName(Mockito.any())).thenReturn(true);
        Mockito.when(verifier.isValidEmail(Mockito.any())).thenReturn(true);
        Mockito.when(verifier.isValidPassword(Mockito.any())).thenReturn(true);

        Mockito.when(userService.addNewUser(Mockito.any())).thenReturn(response);
        Mockito.when(userService.isUniqueName(Mockito.any())).thenReturn(true);
        Mockito.when(userService.isUniqueEmail(Mockito.any())).thenReturn(true);

        UserDataDTO dto = new UserDataDTO().setUsername(username).setPassword(password).setEmail(email);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(dto);
        MvcResult result = mvc.perform(post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String json = result.getResponse().getContentAsString();
        UserResponse userResponse = mapper.readValue(json, UserResponse.class);
        assertEquals(username, userResponse.getName());
    }

    @Test
    void changeUser() throws Exception  {
        Mockito.when(verifier.isValidName(Mockito.any())).thenReturn(true);
        Mockito.when(verifier.isValidEmail(Mockito.any())).thenReturn(true);
        Mockito.when(verifier.isValidPassword(Mockito.any())).thenReturn(true);

        Mockito.when(userService.getByName(Mockito.any())).thenReturn(response);
        Mockito.when(userService.changeUser(Mockito.any())).thenReturn(response.setName(newUsername));
        Mockito.when(userService.isUniqueName(Mockito.any())).thenReturn(true);
        Mockito.when(userService.isUniqueEmail(Mockito.any())).thenReturn(true);

        UserDataDTO dto = new UserDataDTO().setUsername(username).setPassword(password).setEmail(email);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(dto);

        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("username", username);

        MvcResult result = mvc.perform(put("/user/change").sessionAttrs(sessionattr)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String json = result.getResponse().getContentAsString();
        UserResponse userResponse = mapper.readValue(json, UserResponse.class);
        assertEquals(newUsername, userResponse.getName());
    }

    @Test
    void deleteUser() throws Exception {
        Mockito.when(userService.deleteUser(Mockito.anyLong())).thenReturn(response);
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("userid", "1");
        MvcResult result = mvc.perform(delete("/user/delete").sessionAttrs(sessionattr))
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
    void getUser() throws Exception {
        Mockito.when(userService.getByName(Mockito.any())).thenReturn(response);
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("username", username);
        MvcResult result = mvc.perform(get("/user/get").sessionAttrs(sessionattr))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        UserResponse userResponse = mapper.readValue(json, UserResponse.class);
        assertEquals(username, userResponse.getName());
    }
}