package org.exercises.loans;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.LinkedHashSet;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoansController.class)
public class LoansControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LoansService loansService;

    @Test
    public void customerLoansEndpointTest() throws Exception {
        String jsonBody = """
                {
                    "age": 26,
                    "cpf": "275.484.389-23",
                    "name": "Vuxaywua Zukiagou",
                    "income": 7000.00,
                    "location": "SP"
                }
                """;

        when(loansService.checkEligibleLoans(objectMapper.readValue(jsonBody, LoansRequestDTO.class)))
                .thenReturn(new LoansResponseDTO("Vuxaywua Zukiagou",
                        new LinkedHashSet<>(List.of(Loan.PERSONAL, Loan.GUARANTEED, Loan.CONSIGNMENT))));

        mockMvc.perform(post("/customer-loans").contentType(MediaType.APPLICATION_JSON).content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                    {
                        "customer": "Vuxaywua Zukiagou",
                        "loans": [
                            {
                                "type": "PERSONAL",
                                "interest_rate": 4
                            },
                            {
                                "type": "GUARANTEED",
                                "interest_rate": 3
                            },
                            {
                                "type": "CONSIGNMENT",
                                "interest_rate": 2
                            }
                        ]
                    }
                    """));
    }

}
