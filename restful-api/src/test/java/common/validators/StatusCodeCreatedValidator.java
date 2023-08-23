package common.validators;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatusCodeCreatedValidator implements Validator {
	@Override
	public void validate(Response response) {
		assertEquals(HttpStatus.SC_CREATED, response.getStatusCode(), response.getBody().asString());
	}
}
