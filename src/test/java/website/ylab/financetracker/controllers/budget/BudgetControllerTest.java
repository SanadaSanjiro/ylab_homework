package website.ylab.financetracker.controllers.budget;

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
import website.ylab.financetracker.in.dto.budget.BudgetResponse;
import website.ylab.financetracker.in.dto.budget.SetBudgetDTO;
import website.ylab.financetracker.service.budget.BudgetService;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BudgetController.class)
class BudgetControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    BudgetService service;

    @Test
    void getBudget() throws Exception {
        Mockito.when(service.getByUserId(Mockito.anyLong())).thenReturn(new BudgetResponse().setLimit(100.0));
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("userid", "1");

        mvc.perform(get("/budget/get").sessionAttrs(sessionattr))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.limit").value(100.0));
    }

    @Test
    void setBudget() throws Exception  {
        Mockito.when(service.setBudget(Mockito.any())).thenReturn(new BudgetResponse().setLimit(100.0));
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("userid", "1");
        SetBudgetDTO dto = new SetBudgetDTO().setLimit(100.0);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(dto);

        mvc.perform(post("/budget/set")
                        .sessionAttrs(sessionattr)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit").value(100.0));

    }
}