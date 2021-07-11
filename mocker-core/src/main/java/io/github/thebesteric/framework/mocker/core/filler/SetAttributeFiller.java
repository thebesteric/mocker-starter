package io.github.thebesteric.framework.mocker.core.filler;

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

        Set<Object> set = (Set<Object>) collectionFiller(mockInstance, classWarp, field, actualType, new HashSet<>(), value);
        field.set(mockInstance, set);
    }
}
