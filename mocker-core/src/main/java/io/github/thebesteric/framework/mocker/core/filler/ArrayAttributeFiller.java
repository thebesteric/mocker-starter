package io.github.thebesteric.framework.mocker.core.filler;

import io.github.thebesteric.framework.mocker.commons.utils.JsonUtils;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
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

    @Override
    public boolean match(Class<?> clazz) {
        return isArray(clazz);
    }

    private static final Map<Object, Map<Field, Integer>> RECURSION_TIMES = new HashMap<>();

    @Override
    public void doPopulateInstance(Object mockInstance, Field field, Object value) throws Throwable {
        Class<?> componentClassType = field.getType().getComponentType();

        if (componentClassType == mockInstance.getClass()) {
            Map<Field, Integer> fieldMap = RECURSION_TIMES.getOrDefault(mockInstance, new HashMap<>(16));
            // recursion times
            Integer recurseTimes = fieldMap.getOrDefault(field, 0);
            if (recurseTimes++ == getProperties().getRevisionTimes()) {
                RECURSION_TIMES.remove(mockInstance);
                return;
            }
            fieldMap.put(field, recurseTimes);
            RECURSION_TIMES.put(mockInstance, fieldMap);
        }


        Object newArray = mockValue(componentClassType, mockInstance, value);
        field.set(mockInstance, value != null ? value : newArray);
    }

    @Override
    public Object mockValue(Class<?> clazz, Object instance, Object value) throws Throwable {
        if (value != null) {
            return value;
        }
        Object newArray = Array.newInstance(clazz, getProperties().getIteratorLength());
        for (int i = 0; i < Array.getLength(newArray); i++) {
            for (AttributeFiller attributeFiller : getAttributeFillers()) {
                if (attributeFiller.match(clazz)) {
                    value = attributeFiller.mockValue(clazz, instance, null);
                    break;
                }
            }
            Array.set(newArray, i, value);
        }
        return newArray;
    }
}
