package io.github.thebesteric.framework.mocker.core.filler;

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

        List<Object> list = (List<Object>) collectionFiller(mockInstance, classWarp, field, actualType, new ArrayList<>(), value);
        field.set(mockInstance, list);
    }
}
