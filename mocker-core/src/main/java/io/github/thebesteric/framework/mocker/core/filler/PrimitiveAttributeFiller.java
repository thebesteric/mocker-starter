package io.github.thebesteric.framework.mocker.core.filler;

import io.github.thebesteric.framework.mocker.core.domain.ClassWarp;

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

    private ClassWarp classWarp;

    @Override
    public boolean match(ClassWarp classWarp) {
        this.classWarp = classWarp;
        return isPrimitive(classWarp.getClazz());
    }

    @Override
    public void doPopulateInstance(Object mockInstance, Field field, Object value) throws Throwable {
        Class<?> fieldClassType = field.getType();
        if (fieldClassType == Object.class) {
            fieldClassType = classWarp.getClazz();
        }
        field.set(mockInstance, mockValue(fieldClassType, mockInstance, value));
    }

    @Override
    public Object doMockValue(Class<?> fieldClassType, Object instance, String value) {
        if (fieldClassType == char.class) {
            return value != null ? value.charAt(0) : '0';
        } else if (fieldClassType == byte.class) {
            return value != null ? value.getBytes(StandardCharsets.UTF_8)[0] : RANDOM.nextInt() + 1;
        } else if (fieldClassType == short.class) {
            return value != null ? Short.parseShort(value) : RANDOM.nextInt(100) + 1;
        } else if (fieldClassType == int.class) {
            return value != null ? Integer.parseInt(value) : RANDOM.nextInt();
        } else if (fieldClassType == long.class) {
            return value != null ? Long.parseLong(value) : RANDOM.nextLong();
        } else if (fieldClassType == float.class) {
            return value != null ? Float.parseFloat(value) : RANDOM.nextFloat() * 100;
        } else if (fieldClassType == double.class) {
            return value != null ? Double.parseDouble(value) : RANDOM.nextDouble() * 100;
        } else if (fieldClassType == boolean.class) {
            return value != null ? Boolean.parseBoolean(value) : RANDOM.nextBoolean();
        }
        return value;
    }
}
