package com.sourceflag.framework.mocker.core.filler;

import java.lang.reflect.Field;
import java.util.Random;

/**
 * AttrFiller
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-26 18:02
 * @since 1.0
 */
public interface AttributeFiller {

    Random RANDOM = new Random(System.nanoTime());

    /**
     * 属性填充
     *
     * @param mockInstance mock 实例
     * @throws Exception exception
     * @author Eric
     * @date 2021/5/27 18:41
     */
    default void fill(Object mockInstance) throws Exception {
        Class<?> currentClass = mockInstance.getClass();
        do {
            Field[] fields = currentClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                doFill(mockInstance, field);
            }
            currentClass = currentClass.getSuperclass();
        } while (currentClass != null && currentClass != Object.class);
    }

    /**
     * 属性填充
     *
     * @param mockInstance mock 实例
     * @param field        字段
     * @throws Exception exception
     * @author Eric
     * @date 2021/5/27 18:42
     */
    void doFill(Object mockInstance, Field field) throws Exception;

    /**
     * 是否是包装类型
     *
     * @param clazz class
     * @return boolean
     * @author Eric
     * @date 2021/5/27 18:43
     */
    default boolean isWrap(Class<?> clazz) {
        try {
            return ((Class<?>) clazz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 是否是基本类型
     *
     * @param clazz class
     * @return boolean
     * @author Eric
     * @date 2021/5/27 18:44
     */
    default boolean isPrimitive(Class<?> clazz) {
        return clazz.isPrimitive();
    }
}
