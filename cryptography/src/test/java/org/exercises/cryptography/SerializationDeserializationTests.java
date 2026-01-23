package org.exercises.cryptography;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;


@JsonTest
public class SerializationDeserializationTests {

    @Autowired
    private JacksonTester<UserDTO> tester;

    @Test
    public void testSerialize() throws Exception {
        UserDTO dto = new UserDTO(0L, "userDocument", "creditCardToken", 100L);
        assertThat(tester.write(dto))
                .hasJsonPathNumberValue("$.id", 0L)
                .hasJsonPathStringValue("$.userDocument",  "userDocument")
                .hasJsonPathStringValue("$.creditCardToken", "creditCardToken")
                .hasJsonPathNumberValue("$.value",  100L);
    }

    @Test
    public void testDeserialize() throws Exception {
        String json = "{\"id\":0,\"userDocument\":\"userDocument\",\"creditCardToken\":\"creditCardToken\",\"value\":100}";
        assertThat(tester.parse(json))
                .isEqualTo(new UserDTO(0L, "userDocument", "creditCardToken", 100L));
    }

    @Test
    public void testDeserializeWithNullValue() throws Exception {
        String nullId = "{\"userDocument\":\"userDocument\",\"creditCardToken\":\"creditCardToken\",\"value\":100}";
        assertThat(tester.parse(nullId))
                .isEqualTo(new UserDTO(null, "userDocument", "creditCardToken", 100L));

        String nullUserDocument = "{\"id\":0,\"creditCardToken\":\"creditCardToken\",\"value\":100}";
        assertThat(tester.parse(nullUserDocument))
                .isEqualTo(new UserDTO(0L, null, "creditCardToken", 100L));

        String nullCreditCardToken = "{\"id\":0,\"userDocument\":\"userDocument\",\"value\":100}";
        assertThat(tester.parse(nullCreditCardToken))
                .isEqualTo(new UserDTO(0L, "userDocument", null, 100L));

        String nullValue = "{\"id\":0,\"userDocument\":\"userDocument\",\"creditCardToken\":\"creditCardToken\"}";
        assertThat(tester.parse(nullValue))
                .isEqualTo(new UserDTO(0L, "userDocument", "creditCardToken", null));

    }

}
