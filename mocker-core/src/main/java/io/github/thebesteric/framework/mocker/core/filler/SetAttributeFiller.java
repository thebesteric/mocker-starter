package io.github.thebesteric.framework.mocker.core.filler;

import io.github.thebesteric.framework.mocker.annotation.MockProp;
import io.github.thebesteric.framework.mocker.core.domain.ClassWarp;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * ArrayAttributeFiller
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-07-08 23:07
 * @since 1.0
 */
@RequiredArgsConstructor
public class SetAttributeFiller extends AbstractIteratorAttributeFiller {
    @Override
    public boolean match(ClassWarp classWarp) {
        return isSet(classWarp.getClazz());
    }

    @Override
    public void doPopulateInstance(Object mockInstance, Field field, Object value) throws Throwable {
        Class<?> actualType = getActualType(field);
        ClassWarp classWarp = new ClassWarp(actualType);

        Set<Object> set = new HashSet<>();
        if (field.isAnnotationPresent(MockProp.class) && (isWrap(actualType) || isString(actualType))) {
            MockProp mockProp = field.getAnnotation(MockProp.class);
            String[] valueArr = mockProp.value();
            for (String str : valueArr) {
                set.add(mockPropValue(actualType, str));
            }
        } else {
            // Step.1: Confirm repeat length
            int repeatLength = getRepeatLength(field);

            // Step.2: Mock Value
            for (int i = 0; i < repeatLength; i++) {
                for (AttributeFiller attributeFiller : getAttributeFillers()) {
                    if (attributeFiller.match(classWarp)) {
                        value = attributeFiller.mockValue(actualType, mockInstance, null);
                        if(isComplex(value.getClass())) {
                            // Step.3: Assign Value
                            populateMockPropValue(value, i);
                        }
                        break;
                    }
                }
                set.add(value);
            }
        }
        field.set(mockInstance, set);
    }
}
