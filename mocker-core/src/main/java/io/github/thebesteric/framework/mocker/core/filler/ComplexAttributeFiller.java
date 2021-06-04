package io.github.thebesteric.framework.mocker.core.filler;

import io.github.thebesteric.framework.mocker.configuration.MockerProperties;
import io.github.thebesteric.framework.mocker.commons.utils.JsonUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Field;
import java.util.List;

/**
 * ComplexAttributeFiller
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-06-01 01:42
 * @since 1.0
 */
@RequiredArgsConstructor
public class ComplexAttributeFiller implements AttributeFiller {

    @Getter
    private final List<AttributeFiller> attributeFillers;

    private final MockerProperties properties;

    @Override
    public boolean match(Field field) {
        return isComplex(field.getType());
    }

    private static int recursionTimes = 0;

    @Override
    public void doPopulateInstance(Object mockInstance, Field field, Object value) throws Throwable {

        // Control the number of recursions
        if (value == null) {
            value = mockInstance;
            if (recursionTimes++ == properties.getRevisionTimes()) {
                recursionTimes = 0;
                return;
            }
        }

        // populate attributes
        for (Field objectField : value.getClass().getDeclaredFields()) {
            for (AttributeFiller attributeFiller : attributeFillers) {
                if (attributeFiller.match(objectField)) {
                    objectField.setAccessible(true);
                    Object fieldValue = objectField.get(value);
                    attributeFiller.populateInstance(value, objectField, fieldValue);
                    break;
                }
            }
        }

        // prevent reflection exceptions for recursions
        if (mockInstance == value) {
            value = JsonUtils.deepCopy(value);
        }
        field.set(mockInstance, value);
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
