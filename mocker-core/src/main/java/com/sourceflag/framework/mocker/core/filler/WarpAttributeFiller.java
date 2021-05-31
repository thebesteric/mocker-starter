package com.sourceflag.framework.mocker.core.filler;

import java.lang.reflect.Field;

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
    public void doFill(Object mockInstance, Field field) throws Exception {
        Class<?> fieldClassType = field.getType();
        if (isWrap(fieldClassType)) {
            if (fieldClassType == Character.class) {
                field.set(mockInstance, '0');
            } else if (fieldClassType == Byte.class) {
                field.set(mockInstance, RANDOM.nextInt() + 1);
            } else if (fieldClassType == Short.class) {
                field.set(mockInstance, RANDOM.nextInt(100) + 1);
            } else if (fieldClassType == Integer.class) {
                field.set(mockInstance, RANDOM.nextInt());
            } else if (fieldClassType == Long.class) {
                field.set(mockInstance, RANDOM.nextLong());
            } else if (fieldClassType == Float.class) {
                field.set(mockInstance, RANDOM.nextFloat() * 100);
            } else if (fieldClassType == Double.class) {
                field.set(mockInstance, RANDOM.nextDouble() * 100);
            } else if (fieldClassType == Boolean.class) {
                field.set(mockInstance, RANDOM.nextBoolean());
            }
        }
    }
}
