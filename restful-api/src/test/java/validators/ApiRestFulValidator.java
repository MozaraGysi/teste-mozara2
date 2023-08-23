package validators;

import common.validators.ResponseDTOValidator;
import common.validators.Validator;
import dto.response.ApiRestFulResponseDTO;
import io.restassured.response.Response;

public class ApiRestFulValidator implements Validator, ResponseDTOValidator {

	@Override
	public void validate(Response response) {
		ApiRestFulResponseDTO apiRestFulResponseDTO = new ApiRestFulResponseDTO().fromJsonString(response.getBody().asString());
		validateNotNull(apiRestFulResponseDTO);
	}
}
