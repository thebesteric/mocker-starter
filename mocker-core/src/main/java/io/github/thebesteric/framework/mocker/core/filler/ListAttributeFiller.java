package io.github.thebesteric.framework.mocker.core.filler;

import io.github.thebesteric.framework.mocker.core.domain.ClassWarp;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
        ParameterizedType genericType = (ParameterizedType) field.getGenericType();
        Type[] actualTypeArguments = genericType.getActualTypeArguments();
        Class<?> actualType = Object.class;
        if (actualTypeArguments != null && actualTypeArguments.length > 0) {
            Type actualTypeArgument = actualTypeArguments[actualTypeArguments.length - 1];
            actualType = Class.forName(actualTypeArgument.getTypeName());
        }

        ClassWarp classWarp = new ClassWarp(actualType);
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < getProperties().getIteratorLength(); i++) {
            for (AttributeFiller attributeFiller : getAttributeFillers()) {
                if (attributeFiller.match(classWarp)) {
                    value = attributeFiller.mockValue(actualType, mockInstance, null);
                    break;
                }
            }
            list.add(value);
        }

        field.set(mockInstance, list);
    }
}
