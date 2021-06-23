package io.github.thebesteric.framework.mocker.core.filler;

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
public class PrimitiveAttributeFiller extends AbstractAttributeFiller {

    @Override
    public boolean match(Class<?> clazz) {
        return isPrimitive(clazz);
    }

    @Override
    public void doPopulateInstance(Object mockInstance, Field field, Object value) throws Throwable {
        Class<?> fieldClassType = field.getType();
        field.set(mockInstance, mockValue(fieldClassType, mockInstance, value));
    }

    @Override
    public Object mockValue(Class<?> fieldClassType, Object instance, Object value) {
        String strValue = String.valueOf(value);
        if (fieldClassType == char.class) {
            return value != null ? strValue.charAt(0) : '0';
        } else if (fieldClassType == byte.class) {
            return value != null ? strValue.getBytes(StandardCharsets.UTF_8)[0] : RANDOM.nextInt() + 1;
        } else if (fieldClassType == short.class) {
            return value != null ? Short.parseShort(strValue) : RANDOM.nextInt(100) + 1;
        } else if (fieldClassType == int.class) {
            return value != null ? Integer.parseInt(strValue) : RANDOM.nextInt();
        } else if (fieldClassType == long.class) {
            return value != null ? Long.parseLong(strValue) : RANDOM.nextLong();
        } else if (fieldClassType == float.class) {
            return value != null ? Float.parseFloat(strValue) : RANDOM.nextFloat() * 100;
        } else if (fieldClassType == double.class) {
            return value != null ? Double.parseDouble(strValue) : RANDOM.nextDouble() * 100;
        } else if (fieldClassType == boolean.class) {
            return value != null ? Boolean.parseBoolean(strValue) : RANDOM.nextBoolean();
        }
        return strValue;
    }
}
