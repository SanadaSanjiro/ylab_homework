package website.ylab.financetracker.controllers.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import website.ylab.financetracker.in.dto.api.EmailNotification;
import website.ylab.financetracker.service.api.ApiService;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApiController.class)
class ApiControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    ApiService service;

    @Test
    void getBudgetExceedance() throws Exception {
        Mockito.when(service.isExceeded(Mockito.anyLong())).thenReturn(true);
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("userid", "1");

        MvcResult result = mvc.perform(get("/api/exceedance")
                        .sessionAttrs(sessionattr))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(Boolean.parseBoolean(result.getResponse().getContentAsString()));
    }

    @Test
    void getEmail() throws Exception {
        Mockito.when(service.getEmailNotifications()).thenReturn(
                List.of(new EmailNotification("email", "body")));
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("userid", "1");

        MvcResult result = mvc.perform(get("/api/email")
                        .sessionAttrs(sessionattr))
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        List<EmailNotification> actual = mapper.readValue(json, new TypeReference<List<EmailNotification>>() {});

        assertEquals("email", actual.get(0).getEmail());
        assertEquals("body", actual.get(0).getBody());
    }
}