package io.github.thebesteric.framework.mocker.core.filler;

import io.github.thebesteric.framework.mocker.annotation.MockIgnore;
import io.github.thebesteric.framework.mocker.annotation.MockProp;
import io.github.thebesteric.framework.mocker.commons.utils.CollectionUtils;
import io.github.thebesteric.framework.mocker.commons.utils.JsonUtils;
import io.github.thebesteric.framework.mocker.commons.utils.ReflectUtils;
import io.github.thebesteric.framework.mocker.core.domain.ClassWarp;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ComplexAttributeFiller
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-06-01 01:42
 * @since 1.0
 */
@Slf4j
public class ComplexAttributeFiller extends AbstractAttributeFiller {


    @Override
    public boolean match(ClassWarp classWarp) {
        Class<?> clazz = classWarp.getClazz();
        return isComplex(clazz) && !classWarp.isArray();
    }

    @Override
    public void doPopulateInstance(Object mockInstance, Field field, Object value) throws Throwable {

        // control the number of recursions
        Object currentObject = value;
        if (currentObject == null) {
            int recurseTimes = getInstanceFieldRecurseTimes(mockInstance, field);
            // recursion times
            if (recurseTimes++ == getProperties().getRevisionTimes()) {
                RECURSION_INSTANCE_FIELD_MAP.remove(mockInstance);
                return;
            }
            addInstanceFieldRecurseTimes(mockInstance, field, recurseTimes);
        }

        // populate attributes
        currentObject = mockValue(field.getType(), mockInstance, value);

        // prevent reflection exceptions for recursions
        if (mockInstance == currentObject) {
            currentObject = JsonUtils.deepCopy(currentObject);
        }

        field.set(mockInstance, currentObject);

        // need to clear the recursion times in finally
        clearMockInstanceRecurseTimes();
    }

    private static int mockInstanceRecurseTimes = 0;

    @Override
    public Object mockValue(Class<?> clazz, Object mockInstance, Object value) throws Throwable {
        Object instance = mockInstance;
        // Create new instance if not same type object
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
            if (mockInstanceRecurseTimes++ == getProperties().getRevisionTimes()) {
                clearMockInstanceRecurseTimes();
                return instance;
            }
        }
        // Processes arguments of primitive or String types
        if (value != null && clazz != BigDecimal.class
                && (value.getClass().isPrimitive() || isString(value.getClass()))) {
            clazz = value.getClass();
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

        allFields = allFields.stream().filter(field -> !ReflectUtils.isFinal(field)).collect(Collectors.toList());

        // Assign the fields in turn
        if (allFields.size() > 0 && !clazz.isPrimitive() && clazz != String.class) {
            for (Field objectField : allFields) {
                ClassWarp classWarp = new ClassWarp(objectField.getType());
                for (AttributeFiller attributeFiller : getAttributeFillers()) {
                    if (attributeFiller.match(classWarp) && !objectField.isAnnotationPresent(MockIgnore.class)) {
                        objectField.setAccessible(true);
                        if (objectField.isAnnotationPresent(MockProp.class) &&
                                (isPrimitive(objectField.getType())
                                        || isWrap(objectField.getType())
                                        || isString(objectField.getType()))) {
                            MockProp mockProp = objectField.getAnnotation(MockProp.class);
                            attributeFiller.populateInstance(instance, objectField, mockProp.value());
                        } else {
                            try {
                                Object fieldValue = objectField.get(instance);
                                attributeFiller.populateInstance(instance, objectField, fieldValue);
                            } catch (Exception ex) {
                                log.info(ex.getMessage());
                            }
                        }
                        break;
                    }
                }
            }
        } else if (value != null) {
            instance = populate(value.getClass(), value, null);
        }
        return instance;
    }

    public static void clearMockInstanceRecurseTimes() {
        mockInstanceRecurseTimes = 0;
    }

    /**
     * NonValue Class
     *
     * @author Eric
     * @date 2021/6/1 2:27
     */
    public static class NonType {
        private NonType() {
            super();
        }
    }
}
