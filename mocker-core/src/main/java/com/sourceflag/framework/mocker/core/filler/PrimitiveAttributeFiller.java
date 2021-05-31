package com.sourceflag.framework.mocker.core.filler;

import java.lang.reflect.Field;

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
    public void doFill(Object mockInstance, Field field) throws Exception {
        Class<?> fieldClassType = field.getType();
        if (isPrimitive(fieldClassType)) {
            if (fieldClassType == char.class) {
                field.set(mockInstance, '0');
            } else if (fieldClassType == byte.class) {
                field.set(mockInstance, RANDOM.nextInt() + 1);
            } else if (fieldClassType == short.class) {
                field.set(mockInstance, RANDOM.nextInt(100) + 1);
            } else if (fieldClassType == int.class) {
                field.set(mockInstance, RANDOM.nextInt());
            } else if (fieldClassType == long.class) {
                field.set(mockInstance, RANDOM.nextLong());
            } else if (fieldClassType == float.class) {
                field.set(mockInstance, RANDOM.nextFloat() * 100);
            } else if (fieldClassType == double.class) {
                field.set(mockInstance, RANDOM.nextDouble() * 100);
            } else if (fieldClassType == boolean.class) {
                field.set(mockInstance, RANDOM.nextBoolean());
            }
        }
    }
}
