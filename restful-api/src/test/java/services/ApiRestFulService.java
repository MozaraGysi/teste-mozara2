package services;

import common.validators.StatusCodeOKValidator;
import common.validators.Validator;
import dto.request.ApiRestFulRequestDTO;
import fixture.ApiRestFulRequestDTOFixture;
import io.restassured.response.Response;
import org.json.JSONObject;
import utils.APIClient;
import utils.Utils;
import validators.ApiRestFulValidator;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class ApiRestFulService {

	private ApiRestFulService() {
		throw new IllegalStateException("CreateUserService is a Service Class, is not to be instantiated");
	}

	public static void postAddObject() {
		ApiRestFulRequestDTO apiRestFulRequestDTO = ApiRestFulRequestDTOFixture.get().withData().build();

		Response response = APIClient.postAddObject(apiRestFulRequestDTO);

		//O retorno da api está sendo 200, porém o retorno comum para método POST é 201
		List<Validator> validators = List.of(new StatusCodeOKValidator(), new ApiRestFulValidator());
		validators.forEach(validator -> validator.validate(response));

		Utils.validateSchema(response, "add-object-schema.json");
	}

	public static void putUpdateObject() {
		ApiRestFulRequestDTO apiRestFulRequestDTO = ApiRestFulRequestDTOFixture.get().withData().build();

		Response response = APIClient.postAddObject(apiRestFulRequestDTO);

		String responseBody = response.getBody().asString();

		JSONObject jsonObject = new JSONObject(responseBody);
		String id = jsonObject.getString("id");

		apiRestFulRequestDTO = ApiRestFulRequestDTOFixture.get().withDataUpdate().build();

		Response response2 = APIClient.putUpdateObject(apiRestFulRequestDTO, id);

		System.out.println(response2.getBody().asString());

		//O retorno da api está sendo 200, porém o retorno comum para método PUT é 201
		List<Validator> validators = List.of(new StatusCodeOKValidator(), new ApiRestFulValidator());
		validators.forEach(validator -> validator.validate(response2));

		Utils.validateSchema(response, "add-object-schema.json");
	}

	public static void patchPartiallyUpdateObject() {
		ApiRestFulRequestDTO apiRestFulRequestDTO = ApiRestFulRequestDTOFixture.get().withData().build();

		Response response = APIClient.postAddObject(apiRestFulRequestDTO);

		String responseBody = response.getBody().asString();

		JSONObject jsonObject = new JSONObject(responseBody);
		String id = jsonObject.getString("id");

		apiRestFulRequestDTO = ApiRestFulRequestDTOFixture.get().withName().build();

		Response response2 = APIClient.patchPartiallyUpdateObject(apiRestFulRequestDTO, id);

		System.out.println(response2.getBody().asString());

		List<Validator> validators = List.of(new StatusCodeOKValidator(), new ApiRestFulValidator());
		validators.forEach(validator -> validator.validate(response2));

		Utils.validateSchema(response, "add-object-schema.json");
	}

	public static void deleteObject() {
		ApiRestFulRequestDTO apiRestFulRequestDTO = ApiRestFulRequestDTOFixture.get().withData().build();

		Response response = APIClient.postAddObject(apiRestFulRequestDTO);

		String responseBody = response.getBody().asString();

		JSONObject jsonObject = new JSONObject(responseBody);
		String id = jsonObject.getString("id");

		Response response2 = APIClient.deleteObject(apiRestFulRequestDTO, id);

		String responseBody1 = response2.getBody().asString();
		JSONObject jsonObject2 = new JSONObject(responseBody1);
		String mensagemAPI = jsonObject2.getString("message");

		//O código padrão para métodos DELETE geralmente é 204 sem retorno de response
		List<Validator> validators = List.of(new StatusCodeOKValidator(), new ApiRestFulValidator());
		validators.forEach(validator -> validator.validate(response2));

		Assertions.assertEquals(mensagemAPI, "Object with id = "+id+" has been deleted.");
	}
}
