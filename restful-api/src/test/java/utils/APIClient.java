package utils;

import common.AbstractDTO;
import dto.request.ApiRestFulRequestDTO;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static data.TestData.getBaseUrl;
import static java.util.Objects.nonNull;

public class APIClient {

	private APIClient() {
		throw new IllegalStateException("APIClient is a Utility class, aren't to be instantiated ");
	}

	private static RequestSpecification getRequestSpecification(AbstractDTO requestDTO) {
		RestAssured.baseURI = getBaseUrl();

		RequestSpecification request = RestAssured.given();

		if (nonNull(requestDTO)) {
			request.header("Content-Type", requestDTO.getContentType());
			request.headers(requestDTO.headers());
			request.body(requestDTO.body());
			request.queryParams(requestDTO.queryParams());
		}

		return request;
	}

	private static Response post(AbstractDTO requestDTO, String requestUri) {
		RequestSpecification request = getRequestSpecification(requestDTO);
		Response response = request.post(requestUri);
		return response;
	}

	private static Response put(AbstractDTO requestDTO, String requestUri) {
		RequestSpecification request = getRequestSpecification(requestDTO);
		Response response = request.put(requestUri);
		System.out.println(requestUri);
		return response;
	}

	private static Response patch(AbstractDTO requestDTO, String requestUri) {
		RequestSpecification request = getRequestSpecification(requestDTO);
		Response response = request.patch(requestUri);
		return response;
	}

	private static Response delete(AbstractDTO requestDTO, String requestUri) {
		RequestSpecification request = getRequestSpecification(requestDTO);
		Response response = request.delete(requestUri);
		System.out.println(requestUri);
		return response;
	}

	public static Response postAddObject(ApiRestFulRequestDTO apiRestFulRequestDTO) {
		return post(apiRestFulRequestDTO, "https://api.restful-api.dev/objects");
	}

	public static Response putUpdateObject(ApiRestFulRequestDTO apiRestFulRequestDTO, String id) {
		return put(apiRestFulRequestDTO, "https://api.restful-api.dev/objects/"+ id);
	}

	public static Response patchPartiallyUpdateObject(ApiRestFulRequestDTO apiRestFulRequestDTO, String id) {
		return patch(apiRestFulRequestDTO, "https://api.restful-api.dev/objects/"+ id);
	}

	public static Response deleteObject(ApiRestFulRequestDTO apiRestFulRequestDTO, String id) {
		return delete(apiRestFulRequestDTO, "https://api.restful-api.dev/objects/"+ id);
	}
}
