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
     * 是否匹配
     *
     * @param field 字段
     * @return boolean
     * @author Eric
     * @date 2021/6/1 1:47
     */
    boolean match(Field field);

    /**
     * 属性填充
     *
     * @param mockInstance mock 实例
     * @param field        字段
     * @param value        字符串值
     * @throws Throwable throwable
     * @author Eric
     * @date 2021/6/1 0:17
     */
    default void populateInstance(Object mockInstance, Field field, Object value) throws Throwable {
        field.setAccessible(true);
        doPopulateInstance(mockInstance, field, value);
    }

    /**
     * 属性填充
     *
     * @param mockInstance mock 实例
     * @throws Throwable throwable
     * @author Eric
     * @date 2021/5/27 18:41
     */
    default void populateInstance(Object mockInstance) throws Throwable {
        Class<?> currentClass = mockInstance.getClass();
        do {
            Field[] fields = currentClass.getDeclaredFields();
            for (Field field : fields) {
                if (match(field)) {
                    field.setAccessible(true);
                    doPopulateInstance(mockInstance, field);
                }
            }
            currentClass = currentClass.getSuperclass();
        } while (currentClass != null && currentClass != Object.class);
    }

    /**
     * 属性填充（随机填充）
     *
     * @param mockInstance mock 实例
     * @param field        字段
     * @throws Throwable throwable
     * @author Eric
     * @date 2021/5/27 18:42
     */
    default void doPopulateInstance(Object mockInstance, Field field) throws Throwable {
        doPopulateInstance(mockInstance, field, null);
    }

    /**
     * 属性填充（指定填充）
     *
     * @param mockInstance mock 实例
     * @param field        字段
     * @param value        值
     * @throws Throwable throwable
     * @author Eric
     * @date 2021/6/1 0:15
     */
    void doPopulateInstance(Object mockInstance, Field field, Object value) throws Throwable;

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

    /**
     * 是否是复合类型
     *
     * @param clazz class
     * @return boolean
     * @author Eric
     * @date 2021/5/27 18:44
     */
    default boolean isComplex(Class<?> clazz) {
        return !isPrimitive(clazz) && !isWrap(clazz) && clazz != String.class && ComplexAttributeFiller.NonValue.class != clazz;
    }
}
