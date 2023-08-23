package fixture;

import dto.request.ApiRestFulRequestDTO;

import java.util.HashMap;
import java.util.Map;

public class ApiRestFulRequestDTOFixture {

	private ApiRestFulRequestDTO apiRestFulRequestDTO;

	private ApiRestFulRequestDTOFixture() {
		apiRestFulRequestDTO = new ApiRestFulRequestDTO();
	}

	public static ApiRestFulRequestDTOFixture get() {
		return new ApiRestFulRequestDTOFixture();
	}

	public ApiRestFulRequestDTO build() {
		return apiRestFulRequestDTO;
	}

	public ApiRestFulRequestDTOFixture withData() {
		apiRestFulRequestDTO.setName("Apple MacBook Pro 16");
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("year", 2019);
		dataMap.put("price", 1849.99);
		dataMap.put("CPU model", "Intel Core i9");
		dataMap.put("Hard disk size", "1 TB");
		apiRestFulRequestDTO.setData(dataMap);
		return this;
	}

	public ApiRestFulRequestDTOFixture withDataUpdate() {
		apiRestFulRequestDTO.setName("Apple MacBook Pro 16 Atualizado");
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("year", 2020);
		dataMap.put("price", 1850.77);
		dataMap.put("CPU model", "Intel Core 20");
		dataMap.put("Hard disk size", "2 TB");
		apiRestFulRequestDTO.setData(dataMap);
		return this;
	}

	public ApiRestFulRequestDTOFixture withName() {
		apiRestFulRequestDTO.setName("Apple MacBook Pro 16 Atualizado");
		return this;
	}
}
