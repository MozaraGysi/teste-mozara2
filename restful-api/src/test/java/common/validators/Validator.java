package common.validators;

import io.restassured.response.Response;

public interface Validator {
	void validate(Response response);
}
