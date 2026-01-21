package org.exercises.authentication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticationApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void shouldSendUnauthorizedResponseWhenNoTokenIsProvided() {
		ResponseEntity<Void> response = restTemplate.getForEntity("/protected", Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Test
	void shouldSendUnauthorizedResponseWhenWrongTokenIsProvided() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth("wrongToken");
		HttpEntity<Void> request = new HttpEntity<>(headers);
		ResponseEntity<Void> response = restTemplate.exchange("/protected", HttpMethod.GET, request, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Test
	void shouldSendOkResponseWhenRightTokenIsProvided() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth("rightToken");
		HttpEntity<Void> request = new HttpEntity<>(headers);
		ResponseEntity<Void> response = restTemplate.exchange("/protected", HttpMethod.GET, request, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}
