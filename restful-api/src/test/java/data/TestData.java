package data;

public class TestData {

	private TestData() {
		throw new IllegalStateException("TestData is a Utility Class, is not to be instantiated");
	}

	public static String getBaseUrl() {
		return "https://restful-api.dev";
	}
}
