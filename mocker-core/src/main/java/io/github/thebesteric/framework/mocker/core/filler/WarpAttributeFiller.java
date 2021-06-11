package io.github.thebesteric.framework.mocker.core.filler;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

/**
 * WarpAttributeFiller
 * <p>
 * 包装数据类型填充器
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-27 18:26
 * @since 1.0
 */
public class WarpAttributeFiller extends AbstractAttributeFiller {

    @Override
    public boolean match(Class<?> clazz) {
        return isWrap(clazz);
    }

    @Override
    public void doPopulateInstance(Object mockInstance, Field field, Object value) throws Exception {
        Class<?> fieldClassType = field.getType();
        field.set(mockInstance, mockValue(fieldClassType, mockInstance, value));
    }

    @Override
    public Object mockValue(Class<?> fieldClassType, Object instance, Object value) {
        String strValue = String.valueOf(value);
        if (fieldClassType == Character.class) {
            return value != null ? strValue.charAt(0) : '0';
        } else if (fieldClassType == Byte.class) {
            return value != null ? strValue.getBytes(StandardCharsets.UTF_8)[0] : RANDOM.nextInt() + 1;
        } else if (fieldClassType == Short.class) {
            return value != null ? Short.parseShort(strValue) : RANDOM.nextInt(100) + 1;
        } else if (fieldClassType == Integer.class) {
            return value != null ? Integer.parseInt(strValue) : RANDOM.nextInt();
        } else if (fieldClassType == Long.class) {
            return value != null ? Long.parseLong(strValue) : RANDOM.nextLong();
        } else if (fieldClassType == Float.class) {
            return value != null ? Float.parseFloat(strValue) : RANDOM.nextFloat() * 100;
        } else if (fieldClassType == Double.class) {
            return value != null ? Double.parseDouble(strValue) : RANDOM.nextDouble() * 100;
        } else if (fieldClassType == Boolean.class) {
            return value != null ? Boolean.parseBoolean(strValue) : RANDOM.nextBoolean();
        }
        return strValue;
    }
}
