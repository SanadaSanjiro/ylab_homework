package website.ylab.financetracker.controllers.target;

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
import website.ylab.financetracker.in.dto.target.SetTargetDTO;
import website.ylab.financetracker.in.dto.target.TargetResponse;
import website.ylab.financetracker.service.targets.TargetService;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class TargetControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    TargetService service;

    @Test
    void getTarget() throws Exception {
        Mockito.when(service.getByUserId(Mockito.anyLong())).thenReturn(new TargetResponse().setAmount(100.0));
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("userid", "1");

        mvc.perform(get("/target/get").sessionAttrs(sessionattr))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.amount").value(100.0));
    }

    @Test
    void setTarget() throws Exception {
        Mockito.when(service.setTarget(Mockito.any())).thenReturn(new TargetResponse().setAmount(100.0));
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
        sessionattr.put("userid", "1");
        SetTargetDTO dto = new SetTargetDTO().setAmount(100.0);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(dto);

        mvc.perform(post("/target/set")
                        .sessionAttrs(sessionattr)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(100.0));
    }
}