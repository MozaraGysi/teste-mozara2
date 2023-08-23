package utils;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class Utils {

	public static boolean validateSchema(Response resp, String jsonSchemaPath) {
		boolean isCheckOk = true;
		try {
			JsonSchemaValidator validator = JsonSchemaValidator.matchesJsonSchemaInClasspath(jsonSchemaPath);
			resp.then().assertThat().body(validator);
		} catch (Exception oEx) {
			isCheckOk = false;
			System.out.println("Failed to validate the schema");
		}
		return isCheckOk;
	}
}
