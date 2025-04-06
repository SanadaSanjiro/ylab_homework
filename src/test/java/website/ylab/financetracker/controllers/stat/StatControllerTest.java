package website.ylab.financetracker.controllers.stat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import website.ylab.financetracker.in.dto.stat.BalanceResponse;
import website.ylab.financetracker.in.dto.stat.CategoryExpensesResponse;
import website.ylab.financetracker.in.dto.stat.ReportResponse;
import website.ylab.financetracker.in.dto.stat.TurnoverResponse;
import website.ylab.financetracker.service.stat.StatService;
import website.ylab.financetracker.service.stat.TurnoverRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatController.class)
class StatControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    StatService service;

    @Test
    void getBalance() throws Exception {
        Mockito.when(service.getBalance(Mockito.anyLong())).thenReturn(new BalanceResponse().setBalance(100.0));
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("userid", "1");

        mvc.perform(get("/stat/balance").sessionAttrs(sessionattr))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.balance").value(100.0));
    }

    @Test
    void getExpenses() throws Exception {
        Mockito.when(service.expensesByCategory(Mockito.anyLong())).thenReturn(
                new CategoryExpensesResponse().setExpenses(Map.of("food", 100.0)));
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("userid", "1");

        MvcResult result = mvc.perform(get("/stat/expenses").sessionAttrs(sessionattr))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        CategoryExpensesResponse cer = mapper.readValue(json, CategoryExpensesResponse.class);
        assertEquals(100.0, cer.getExpenses().get("food"));
    }

    @Test
    void getTurnover() throws Exception {
        Mockito.when(service.getTurnover(Mockito.any())).thenReturn(
                new TurnoverResponse().setIncome(100.0).setOutcome(200.0));
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("userid", "1");

        TurnoverRequest request = new TurnoverRequest();
        request.setStartDate(new Date());
        request.setUserid(1L);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(request);

        MvcResult result = mvc.perform(post("/stat/turnover")
                        .sessionAttrs(sessionattr)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        TurnoverResponse response =  mapper.readValue(json, TurnoverResponse.class);
        assertEquals(100.0, response.getIncome());
        assertEquals(200.0, response.getOutcome());
    }

    @Test
    void getReport() throws Exception {
        Mockito.when(service.getReport(Mockito.anyLong())).thenReturn(
                new ReportResponse()
                        .setBalance(new BalanceResponse().setBalance(100.0))
                        .setCategory(new CategoryExpensesResponse().setExpenses(Map.of("food", 200.0)))
                        .setTurnover(new TurnoverResponse().setIncome(50).setOutcome(75)));
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("userid", "1");

        MvcResult result = mvc.perform(get("/stat/report")
                        .sessionAttrs(sessionattr)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ReportResponse response =  mapper.readValue(json, ReportResponse.class);
        assertEquals(100.0, response.getBalance().getBalance());
        assertEquals(200.0, response.getCategory().getExpenses().get("food"));
        assertEquals(50.0, response.getTurnover().getIncome());
        assertEquals(75.0, response.getTurnover().getOutcome());
    }
}