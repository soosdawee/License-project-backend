package com.license.backend.util;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReflectionUtil {

    public static void copyFields(Object source, Object target) {
        Map<String, Field> sourceFields = getAllFields(source.getClass());
        Map<String, Field> targetFields = getAllFields(target.getClass());

        for (Map.Entry<String, Field> entry : sourceFields.entrySet()) {
            String fieldName = entry.getKey();
            Field sourceField = entry.getValue();
            Field targetField = targetFields.get(fieldName);

            if (targetField != null) {
                try {
                    sourceField.setAccessible(true);
                    targetField.setAccessible(true);
                    targetField.set(target, sourceField.get(source));
                } catch (IllegalAccessException e) {
                    System.out.println("Access error for field: " + fieldName);
                }
            }
        }
    }

    private static Map<String, Field> getAllFields(Class<?> type) {
        Map<String, Field> fields = new LinkedHashMap<>();
        while (type != null && type != Object.class) {
            for (Field field : type.getDeclaredFields()) {
                fields.putIfAbsent(field.getName(), field);
            }
            type = type.getSuperclass();
        }
        return fields;
    }
}
