package tests;

import org.junit.jupiter.api.Test;
import services.ApiRestFulService;

class ApiRestFulTest {

	@Test
	void postAddObject() {
		ApiRestFulService.postAddObject();
	}

	@Test
	void putUpdateObject() {
		ApiRestFulService.putUpdateObject();
	}

	@Test
	void patchPartiallyUpdateObject() {
		ApiRestFulService.patchPartiallyUpdateObject();
	}

	@Test
	void deleteObject() {
		ApiRestFulService.deleteObject();
	}

}
