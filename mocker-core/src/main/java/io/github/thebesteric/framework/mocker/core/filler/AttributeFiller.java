package io.github.thebesteric.framework.mocker.core.filler;

import io.github.thebesteric.framework.mocker.annotation.MockIgnore;
import io.github.thebesteric.framework.mocker.annotation.MockProp;
import io.github.thebesteric.framework.mocker.annotation.MockPropGroup;
import io.github.thebesteric.framework.mocker.commons.utils.ReflectUtils;
import io.github.thebesteric.framework.mocker.commons.utils.ThreadLocalUtils;
import io.github.thebesteric.framework.mocker.core.domain.ClassWarp;

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

    String NULL_VALUE = "null";
    Random RANDOM = new Random(System.nanoTime());

    /**
     * 是否匹配
     *
     * @param classWarp 字段
     * @return boolean
     * @author Eric
     * @date 2021/7/3 0:02
     */
    boolean match(ClassWarp classWarp);

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
        if (!ReflectUtils.isFinal(field)) {
            if (field.isAnnotationPresent(MockProp.class)) {
                MockProp mockProp = field.getAnnotation(MockProp.class);
                MockPropGroup[] mockPropGroups = mockProp.group();
                if (mockPropGroups.length > 0 && value instanceof String[]) {
                    String[] values = (String[]) value;
                    Integer groupPropIndex = (Integer) ThreadLocalUtils.getOrDefault(mockInstance.getClass().getName(), 0);
                    value = values[groupPropIndex];
                }
            }
            String key = field.getDeclaringClass().getName() + "." + field.getName();
            Integer index = (Integer) ThreadLocalUtils.getOrDefault(key, 0);
            if (value instanceof String[]) {
                String[] arr = ((String[]) value);
                // index = Math.min(index, arr.length - 1);
                index = index % arr.length;
                value = arr[index];
            }
            doPopulateInstance(mockInstance, field, value);
            ThreadLocalUtils.put(key, ++index);
        }
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
                ClassWarp classWarp = new ClassWarp(field.getType());
                if (match(classWarp) && !ReflectUtils.isFinal(field)
                        && !field.isAnnotationPresent(MockIgnore.class)) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(MockProp.class)) {
                        MockProp mockProp = field.getAnnotation(MockProp.class);
                        doPopulateInstance(mockInstance, field, mockProp.value());
                    } else {
                        doPopulateInstance(mockInstance, field);
                    }
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
     * 属性填充（指定填充）
     *
     * @param clazz    clazz
     * @param instance instance
     * @param value    value
     * @return Object
     * @throws Throwable throwable
     * @author Eric
     * @date 2021/6/10 1:10
     */
    default Object mockValue(Class<?> clazz, Object instance, Object value) throws Throwable {
        if (value != null) {
            String strValue = null;
            if (value instanceof String[]) {
                String[] arr = (String[]) value;
                if (arr.length > 0) {
                    strValue = arr[0];
                }
            } else {
                strValue = String.valueOf(value);
            }
            return doMockValue(clazz, instance, strValue);
        }
        return doMockValue(clazz, instance, null);
    }

    /**
     * 属性填充（指定填充）
     *
     * @param clazz    clazz
     * @param instance instance
     * @param value    value
     * @return Object
     * @throws Throwable throwable
     * @author Eric
     * @date 2021/6/10 1:10
     */
    default Object doMockValue(Class<?> clazz, Object instance, String value) throws Throwable {
        return null;
    }
}
