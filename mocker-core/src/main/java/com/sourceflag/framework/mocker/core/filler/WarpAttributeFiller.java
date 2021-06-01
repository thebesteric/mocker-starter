package com.sourceflag.framework.mocker.core.filler;

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
public class WarpAttributeFiller implements AttributeFiller {

    @Override
    public boolean match(Field field) {
        return isWrap(field.getType());
    }

    @Override
    public void doPopulateInstance(Object mockInstance, Field field, Object value) throws Exception {
        String strValue = String.valueOf(value);
        Class<?> fieldClassType = field.getType();
        if (fieldClassType == Character.class) {
            field.set(mockInstance, value != null ? strValue.charAt(0) : '0');
        } else if (fieldClassType == Byte.class) {
            field.set(mockInstance, value != null ? strValue.getBytes(StandardCharsets.UTF_8)[0] : RANDOM.nextInt() + 1);
        } else if (fieldClassType == Short.class) {
            field.set(mockInstance, value != null ? Short.parseShort(strValue) : RANDOM.nextInt(100) + 1);
        } else if (fieldClassType == Integer.class) {
            field.set(mockInstance, value != null ? Integer.parseInt(strValue) : RANDOM.nextInt());
        } else if (fieldClassType == Long.class) {
            field.set(mockInstance, value != null ? Long.parseLong(strValue) : RANDOM.nextLong());
        } else if (fieldClassType == Float.class) {
            field.set(mockInstance, value != null ? Float.parseFloat(strValue) : RANDOM.nextFloat() * 100);
        } else if (fieldClassType == Double.class) {
            field.set(mockInstance, value != null ? Double.parseDouble(strValue) : RANDOM.nextDouble() * 100);
        } else if (fieldClassType == Boolean.class) {
            field.set(mockInstance, value != null ? Boolean.parseBoolean(strValue) : RANDOM.nextBoolean());
        }
    }
}
