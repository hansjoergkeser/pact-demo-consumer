package provider.user.service;

import lombok.extern.slf4j.Slf4j;
import org.example.consumer.user.service.schema.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class UserControllerSysITTest {

	private static final String BASE_URL = "http://localhost:8080";

	@Test
	void testGetAllUsers() {
		var response = WebClient
				.create(BASE_URL)
				.get()
				.uri("/user/getAllUsers")
				.retrieve()
				.toEntityList(UserDTO.class)
				.block();

		assert response != null;
		assertEquals(HttpStatus.OK, response.getStatusCode());

		assertTrue(response.hasBody());
		if (response.getBody().size() > 0) {
			log.info("Api-test/System-integrationtest testGetAllUsers: {}", response.getBody());
		}


	}

}
