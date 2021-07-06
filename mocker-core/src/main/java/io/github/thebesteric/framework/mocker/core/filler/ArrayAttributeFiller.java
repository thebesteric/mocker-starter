package io.github.thebesteric.framework.mocker.core.filler;

import io.github.thebesteric.framework.mocker.annotation.MockProp;
import io.github.thebesteric.framework.mocker.core.domain.ClassWarp;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * ArrayAttributeFiller
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-06-09 17:58
 * @since 1.0
 */
@RequiredArgsConstructor
public class ArrayAttributeFiller extends AbstractIteratorAttributeFiller {

    private ClassWarp classWarp;

    @Override
    public boolean match(ClassWarp classWarp) {
        this.classWarp = classWarp;
        // handle clazz = Object[].class type
        return isArray(classWarp.getClazz()) || classWarp.isArray();
    }

    @Override
    public void doPopulateInstance(Object mockInstance, Field field, Object value) throws Throwable {
        Class<?> componentClassType = field.getType().getComponentType();

        if (componentClassType == mockInstance.getClass()) {
            Map<Field, Integer> fieldMap = RECURSION_INSTANCE_FIELD_MAP.getOrDefault(mockInstance, new HashMap<>(16));
            // recursion times
            Integer recurseTimes = fieldMap.getOrDefault(field, 0);
            if (recurseTimes++ == getProperties().getRevisionTimes()) {
                RECURSION_INSTANCE_FIELD_MAP.remove(mockInstance);
                return;
            }
            fieldMap.put(field, recurseTimes);
            RECURSION_INSTANCE_FIELD_MAP.put(mockInstance, fieldMap);
        }

        Object newArray;
        if (field.isAnnotationPresent(MockProp.class)
                && (isPrimitive(componentClassType) || isWrap(componentClassType) || isString(componentClassType))) {
            MockProp mockProp = field.getAnnotation(MockProp.class);
            newArray = mockPropValue(componentClassType, mockProp.value());
            field.set(mockInstance, newArray);
        } else {
            newArray = mockValue(componentClassType, mockInstance, value);
            field.set(mockInstance, value != null && componentClassType != null ? value : newArray);
        }

        classWarp = null;
    }

    public Object mockPropValue(Class<?> clazz, String[] arr) throws Throwable {
        Object newArray = Array.newInstance(clazz, arr.length);
        Object[] objects = null;
        if (clazz == Integer.class || clazz == int.class) {
            objects = Arrays.stream(arr).map(Integer::parseInt).toArray();
        } else if (clazz == Byte.class || clazz == byte.class) {
            objects = Arrays.stream(arr).map(Byte::parseByte).toArray();
        } else if (clazz == Short.class || clazz == short.class) {
            objects = Arrays.stream(arr).map(Short::parseShort).toArray();
        } else if (clazz == Long.class || clazz == long.class) {
            objects = Arrays.stream(arr).map(Long::parseLong).toArray();
        } else if (clazz == Float.class || clazz == float.class) {
            objects = Arrays.stream(arr).map(Float::parseFloat).toArray();
        } else if (clazz == Double.class || clazz == double.class) {
            objects = Arrays.stream(arr).map(Double::parseDouble).toArray();
        } else if (clazz == Boolean.class || clazz == boolean.class) {
            objects = Arrays.stream(arr).map(Boolean::parseBoolean).toArray();
        }

        if (objects != null) {
            for (int i = 0; i < objects.length; i++) {
                Array.set(newArray, i, objects[i]);
            }
            return newArray;
        }

        return arr;
    }

    @Override
    public Object mockValue(Class<?> clazz, Object instance, Object value) throws Throwable {
        if (clazz != null && value != null) {
            return value;
        }
        // clazz is null if attribute clazz set Object[].class
        // e.g: @MockItParam(key = "data", clazz = Order[].class)
        if (clazz == null) {
            clazz = classWarp.getClazz();
        }

        ClassWarp classWarp = new ClassWarp(clazz);
        Object newArray = Array.newInstance(clazz, getProperties().getIteratorLength());
        for (int i = 0; i < Array.getLength(newArray); i++) {
            for (AttributeFiller attributeFiller : getAttributeFillers()) {
                if (attributeFiller.match(classWarp)) {
                    value = attributeFiller.mockValue(clazz, instance, null);
                    break;
                }
            }
            Array.set(newArray, i, value);
        }
        return newArray;
    }
}
