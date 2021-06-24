package io.github.thebesteric.framework.mocker.core.filler;

import io.github.thebesteric.framework.mocker.annotation.MockIgnore;
import io.github.thebesteric.framework.mocker.commons.utils.CollectionUtils;
import io.github.thebesteric.framework.mocker.commons.utils.JsonUtils;
import io.github.thebesteric.framework.mocker.commons.utils.ReflectUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * ComplexAttributeFiller
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-06-01 01:42
 * @since 1.0
 */
public class ComplexAttributeFiller extends AbstractAttributeFiller {


    @Override
    public boolean match(Class<?> clazz) {
        return isComplex(clazz) && !isArray(clazz);
    }


    @Override
    public void doPopulateInstance(Object mockInstance, Field field, Object value) throws Throwable {

        // control the number of recursions
        Object currentObject = value;
        if (currentObject == null) {
            int recurseTimes = getRecurseTimes(mockInstance, field);
            // recursion times
            if (recurseTimes++ == getProperties().getRevisionTimes()) {
                RECURSION_TIMES.remove(mockInstance);
                return;
            }
            addRecurseTimes(mockInstance, field, recurseTimes);
        }

        // populate attributes
        currentObject = mockValue(field.getType(), mockInstance, value);

        // prevent reflection exceptions for recursions
        if (mockInstance == currentObject) {
            currentObject = JsonUtils.deepCopy(currentObject);
        }

        field.set(mockInstance, currentObject);
    }

    private static int times = 0;

    @Override
    public Object mockValue(Class<?> clazz, Object mockInstance, Object value) throws Throwable {
        Object instance = mockInstance;
        // create new instance if not same type object
        if (clazz != mockInstance.getClass() && value == null) {
            Constructor<?> constructor = ReflectUtils.determineConstructor(clazz);
            if (constructor != null) {
                instance = ReflectUtils.newInstance(constructor);
            }
            if (constructor == null || instance.getClass() == Object.class) {
                return null;
            }
        }
        if (mockInstance == instance) {
            instance = JsonUtils.deepCopy(instance);
            if (times++ == getProperties().getRevisionTimes()) {
                times = 0;
                return instance;
            }
        }
        instance = populate(clazz, instance, value);
        return instance;
    }

    private Object populate(Class<?> clazz, Object instance, Object value) throws Throwable {
        List<Field> allFields = CollectionUtils.arrayToList(clazz.getDeclaredFields());

        // Find all fields in the subclass and superclass
        Class<?> currentClass = clazz.getSuperclass();
        while (currentClass != null && currentClass != Object.class) {
            allFields.addAll(Arrays.asList(currentClass.getDeclaredFields()));
            currentClass = currentClass.getSuperclass();
        }

        // Assign the fields in turn
        if (allFields.size() > 0) {
            for (Field objectField : allFields) {
                for (AttributeFiller attributeFiller : getAttributeFillers()) {
                    if (attributeFiller.match(objectField.getType()) && !objectField.isAnnotationPresent(MockIgnore.class)) {
                        objectField.setAccessible(true);
                        Object fieldValue = objectField.get(instance);
                        attributeFiller.populateInstance(instance, objectField, fieldValue);
                        break;
                    }
                }
            }
        } else if (value != null) {
            instance = populate(value.getClass(), value, null);
        }
        return instance;
    }

    /**
     * NonValue Class
     *
     * @author Eric
     * @date 2021/6/1 2:27
     */
    public static class NonValue {
        private NonValue() {
            super();
        }
    }
}
