package dto.request;

import common.AbstractDTO;
import common.NotNull;

import java.util.List;
import java.util.Map;

public class ApiRestFulRequestDTO extends AbstractDTO<ApiRestFulRequestDTO> {

	@NotNull
	private String name;
	private Map<String, Object> data;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
}
