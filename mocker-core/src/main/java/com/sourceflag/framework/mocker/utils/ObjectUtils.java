package com.sourceflag.framework.mocker.utils;

/**
 * BeanUtils
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-28 12:38
 * @since 1.0
 */
public class ObjectUtils {

    public static Object initialValue(Class<?> clazz) {
        Object object = null;
        if (clazz.isPrimitive()) {
            if (clazz == char.class) {
                object = '0';
            } else if (clazz == byte.class) {
                object = 0;
            } else if (clazz == short.class) {
                object = 0;
            } else if (clazz == int.class) {
                object = 0;
            } else if (clazz == long.class) {
                object = 0L;
            } else if (clazz == float.class) {
                object = 0.0F;
            } else if (clazz == double.class) {
                object = 0.0D;
            } else if (clazz == boolean.class) {
                object = false;
            }
        }
        return object;
    }

}
