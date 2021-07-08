package io.github.thebesteric.framework.mocker.core.filler;

import io.github.thebesteric.framework.mocker.annotation.MockProp;
import io.github.thebesteric.framework.mocker.core.domain.ClassWarp;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * ListAttributeFiller
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-06-10 12:08
 * @since 1.0
 */
public class ListAttributeFiller extends AbstractIteratorAttributeFiller {

    @Override
    public boolean match(ClassWarp classWarp) {
        return isList(classWarp.getClazz());
    }

    @Override
    public void doPopulateInstance(Object mockInstance, Field field, Object value) throws Throwable {
        Class<?> actualType = getActualType(field);
        ClassWarp classWarp = new ClassWarp(actualType);

        List<Object> list = new ArrayList<>();
        if (field.isAnnotationPresent(MockProp.class) && (isWrap(actualType) || isString(actualType))) {
            MockProp mockProp = field.getAnnotation(MockProp.class);
            String[] valueArr = mockProp.value();
            for (String str : valueArr) {
                list.add(mockPropValue(actualType, str));
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
                list.add(value);
            }
        }
        field.set(mockInstance, list);
    }
}
