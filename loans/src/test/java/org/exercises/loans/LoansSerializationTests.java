package org.exercises.loans;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.LinkedHashSet;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JsonTest
public class LoansSerializationTests {

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void LoansSerializationTest() {
        JsonNode provided = objectMapper.readTree(
                """
                {
                    "age": 26,
                    "cpf": "275.484.389-23",
                    "name": "Vuxaywua Zukiagou",
                    "income": 7000.00,
                    "location": "SP"
                }
                """
        );

        JsonNode expected = objectMapper.valueToTree(new LoansRequestDTO(
                26,
                "275.484.389-23",
                "Vuxaywua Zukiagou",
                7000.00,
                "SP"
        ));

        assertThat(provided).isEqualTo(expected);
    }

    @Test
    public void LoansDeserializationTest() {
        JsonNode provided = objectMapper.valueToTree(new LoansResponseDTO(
                "Vuxaywua Zukiagou",
                new LinkedHashSet<>(List.of(Loan.PERSONAL, Loan.GUARANTEED, Loan.CONSIGNMENT))
        ));

        JsonNode expected = objectMapper.readTree(
            """
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
                    """
        );

        assertThat(provided).isEqualTo(expected);
    }

}
