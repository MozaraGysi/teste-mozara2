package common.validators;


import common.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public interface ResponseDTOValidator {

	default void validateNotNull(Object responseDTO) {
		try {
			for (Field field : responseDTO.getClass().getDeclaredFields()) {
				if (field.isAnnotationPresent(NotNull.class)) {
					Object currentField = getContentField(field, responseDTO);
					assertNotNull(currentField, field.getName());
					if (currentField.getClass().isAssignableFrom(ArrayList.class)) {
						((ArrayList) currentField).forEach(this::validateNotNull);
					}
					if (isResponseDTOClass(currentField.getClass().getSimpleName())) {
						validateNotNull(currentField);
					}
				}
			}
		} catch (Exception e) {
			assertNotNull(e.getCause(), "Error to access fields from class, look the getters and attributes: " + e.getMessage());
		}
	}

	default Object getContentField(Field field, Object classDTO) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
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
		Method method = classDTO.getClass().getDeclaredMethod(methodName);

		return method.invoke(classDTO);
	}

	private boolean isResponseDTOClass(String nameClass) {
		return nameClass.contains("ResponseDTO");
	}

}
