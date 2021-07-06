package io.github.thebesteric.framework.mocker.core.filler;

import io.github.thebesteric.framework.mocker.core.domain.ClassWarp;

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
    public boolean match(ClassWarp classWarp) {
        return isWrap(classWarp.getClazz());
    }

    @Override
    public void doPopulateInstance(Object mockInstance, Field field, Object value) throws Throwable {
        Class<?> fieldClassType = field.getType();
        field.set(mockInstance, mockValue(fieldClassType, mockInstance, value));
    }

    @Override
    public Object doMockValue(Class<?> fieldClassType, Object instance, String value) {
        if (fieldClassType == Character.class) {
            return value != null ? value.charAt(0) : '0';
        } else if (fieldClassType == Byte.class) {
            return value != null ? value.getBytes(StandardCharsets.UTF_8)[0] : RANDOM.nextInt() + 1;
        } else if (fieldClassType == Short.class) {
            return value != null ? Short.parseShort(value) : RANDOM.nextInt(100) + 1;
        } else if (fieldClassType == Integer.class) {
            return value != null ? Integer.parseInt(value) : RANDOM.nextInt();
        } else if (fieldClassType == Long.class) {
            return value != null ? Long.parseLong(value) : RANDOM.nextLong();
        } else if (fieldClassType == Float.class) {
            return value != null ? Float.parseFloat(value) : RANDOM.nextFloat() * 100;
        } else if (fieldClassType == Double.class) {
            return value != null ? Double.parseDouble(value) : RANDOM.nextDouble() * 100;
        } else if (fieldClassType == Boolean.class) {
            return value != null ? Boolean.parseBoolean(value) : RANDOM.nextBoolean();
        }
        return value;
    }
}
