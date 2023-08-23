package common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractDTO<T> {

	private static final String CONTENT_TYPE = "application/json";

	public T fromJsonString(String jsonString) {
		return new Gson().fromJson(jsonString, (Type) getClass());
	}

	public JsonObject toJson() {
		return new Gson().fromJson(new Gson().toJson(this), JsonObject.class);
	}

	public JsonObject toJson(Object instance) {
		return new Gson().fromJson(new Gson().toJson(instance), JsonObject.class);
	}

	public Map<String, Object> toMap() {
		return new Gson().fromJson(this.toJson().toString(), HashMap.class);
	}

	public String getContentType() {
		return CONTENT_TYPE;
	}

	public Map<String, Object> queryParams() {
		Map<String, Object> fieldsToParams = new HashMap<>();

		try {
			for (Field field : this.getClass().getDeclaredFields()) {
				if (field.isAnnotationPresent(QueryParam.class)) {
					mapBodyOrParams(field, fieldsToParams);
				}
			}
		} catch (Exception e) {
			System.out.println("Error to get query params from class, look the getters and attributes");
		}

		return fieldsToParams;
	}

	public String body() {
		Map<String, Object> fieldsToBody = new HashMap<>();
		try {
			for (Field field : this.getClass().getDeclaredFields()) {
				if (!field.isAnnotationPresent(QueryParam.class) && !field.isAnnotationPresent(DataAttribute.class) && !field.isAnnotationPresent(Header.class)) {
					mapBodyOrParams(field, fieldsToBody);
				}
			}
		} catch (Exception e) {
			System.out.println("Error to get body from class, look the getters and attributes");
		}

		return toJson(fieldsToBody).toString();
	}

	public Map<String, String> headers() {
		Map<String, String> headers = new HashMap<>();
		try {
			for (Field field : this.getClass().getDeclaredFields()) {
				if (field.isAnnotationPresent(Header.class)) {
					Object fieldValue = getFieldValue(field);
					if (fieldValue != null) {
						headers.put(field.getAnnotation(Header.class).value(), String.valueOf(fieldValue));
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error to get body from class, look the getters and attributes");
		}

		return headers;
	}

	private void mapBodyOrParams(Field field, Map<String, Object> fieldsToMap) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Object fieldValue = getFieldValue(field);
		if (fieldValue != null) {
			if (field.isAnnotationPresent(SerializedName.class)) {
				fieldsToMap.put(field.getAnnotation(SerializedName.class).value(), fieldValue);
			} else {
				fieldsToMap.put(field.getName(), fieldValue);
			}
		}
	}

	private Object getFieldValue(Field field) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Class<?> booleanClass = boolean.class;
		String methodName;
		if (field.getType().isAssignableFrom(booleanClass)) {
			if (field.getName().substring(0, 2).contains("is")) {
				methodName = field.getName();
			} else {
				methodName = "is" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
			}
		} else {
			methodName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
		}

		return this.getClass().getDeclaredMethod(methodName).invoke(this);
	}
}
