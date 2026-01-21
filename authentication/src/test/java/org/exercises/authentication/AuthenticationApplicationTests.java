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
		ResponseEntity<Void> response = restTemplate.getForEntity("/requires-token", Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Test
	void shouldSendUnauthorizedResponseWhenWrongTokenIsProvided() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth("wrongToken");
		HttpEntity<Void> request = new HttpEntity<>(headers);
		ResponseEntity<Void> response = restTemplate.exchange("/requires-token", HttpMethod.GET, request, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Test
	void shouldSendOkResponseWhenRightTokenIsProvided() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth("rightToken");
		HttpEntity<Void> request = new HttpEntity<>(headers);
		ResponseEntity<Void> response = restTemplate.exchange("/requires-token", HttpMethod.GET, request, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}

	@Test
	void shouldSendUnauthorizedResponseForAlwaysBlockedEndpointRequests() {
		ResponseEntity<Void> noTokenResponse = restTemplate.getForEntity("/always-blocked", Void.class);
		assertThat(noTokenResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth("wrongToken");
		HttpEntity<Void> wrongTokenRequest = new HttpEntity<>(headers);
		ResponseEntity<Void> wrongTokenResponse = restTemplate.exchange("/always-blocked", HttpMethod.GET, wrongTokenRequest, Void.class);
		assertThat(wrongTokenResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

		headers.setBearerAuth("rightToken");
		HttpEntity<Void> rightTokenRequest = new HttpEntity<>(headers);
		ResponseEntity<Void> rightTokenResponse = restTemplate.exchange("/always-blocked", HttpMethod.GET, rightTokenRequest, Void.class);
		assertThat(rightTokenResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Test
	void shouldSendOkResponseForAlwaysAllowedEndpointRequests() {
		ResponseEntity<Void> noTokenResponse = restTemplate.getForEntity("/always-allowed", Void.class);
		assertThat(noTokenResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth("wrongToken");
		HttpEntity<Void> wrongTokenRequest = new HttpEntity<>(headers);
		ResponseEntity<Void> wrongTokenResponse = restTemplate.exchange("/always-allowed", HttpMethod.GET, wrongTokenRequest, Void.class);
		assertThat(wrongTokenResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

		headers.setBearerAuth("rightToken");
		HttpEntity<Void> rightTokenRequest = new HttpEntity<>(headers);
		ResponseEntity<Void> rightTokenResponse = restTemplate.exchange("/always-allowed", HttpMethod.GET, rightTokenRequest, Void.class);
		assertThat(rightTokenResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}

}
