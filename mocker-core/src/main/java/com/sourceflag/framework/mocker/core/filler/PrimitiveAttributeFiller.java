package com.sourceflag.framework.mocker.core.filler;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

/**
 * PrimitiveAttributeFiller
 * <p>
 * 基本数据类型填充器
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-27 11:57
 * @since 1.0
 */
public class PrimitiveAttributeFiller implements AttributeFiller {

    @Override
    public boolean match(Field field) {
        return isPrimitive(field.getType());
    }

    @Override
    public void doPopulateInstance(Object mockInstance, Field field, Object value) throws Throwable {
        String strValue = String.valueOf(value);
        Class<?> fieldClassType = field.getType();
        if (fieldClassType == char.class) {
            field.set(mockInstance, value != null ? strValue.charAt(0) : '0');
        } else if (fieldClassType == byte.class) {
            field.set(mockInstance, value != null ? strValue.getBytes(StandardCharsets.UTF_8)[0] : RANDOM.nextInt() + 1);
        } else if (fieldClassType == short.class) {
            field.set(mockInstance, value != null ? Short.parseShort(strValue) : RANDOM.nextInt(100) + 1);
        } else if (fieldClassType == int.class) {
            field.set(mockInstance, value != null ? Integer.parseInt(strValue) : RANDOM.nextInt());
        } else if (fieldClassType == long.class) {
            field.set(mockInstance, value != null ? Long.parseLong(strValue) : RANDOM.nextLong());
        } else if (fieldClassType == float.class) {
            field.set(mockInstance, value != null ? Float.parseFloat(strValue) : RANDOM.nextFloat() * 100);
        } else if (fieldClassType == double.class) {
            field.set(mockInstance, value != null ? Double.parseDouble(strValue) : RANDOM.nextDouble() * 100);
        } else if (fieldClassType == boolean.class) {
            field.set(mockInstance, value != null ? Boolean.parseBoolean(strValue) : RANDOM.nextBoolean());
        }
    }
}
