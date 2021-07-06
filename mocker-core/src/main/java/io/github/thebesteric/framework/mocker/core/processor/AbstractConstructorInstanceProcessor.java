package io.github.thebesteric.framework.mocker.core.processor;

import io.github.thebesteric.framework.mocker.commons.utils.CacheUtils;
import io.github.thebesteric.framework.mocker.core.domain.ClassWarp;
import io.github.thebesteric.framework.mocker.core.filler.AttributeFiller;
import io.github.thebesteric.framework.mocker.commons.utils.ReflectUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

/**
 * AbstractConstructorInstanceProcessor
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-31 23:09
 * @since 1.0
 */
public abstract class AbstractConstructorInstanceProcessor extends AbstractInstanceProcessor {

    protected final List<AttributeFiller> attributeFillers;

    public AbstractConstructorInstanceProcessor(CacheUtils cacheUtils, List<AttributeFiller> attributeFillers) {
        super(cacheUtils);
        this.attributeFillers = attributeFillers;
    }

    protected Constructor<?> determineConstructor(Class<?> clazz) {
        return ReflectUtils.determineConstructor(clazz);
    }

    protected Object newInstance(Constructor<?> constructor) throws Throwable {
        return ReflectUtils.newInstance(constructor);
    }

    protected Object populateInstance(Object mockInstance) throws Throwable {
        for (AttributeFiller attributeFiller : attributeFillers) {
            attributeFiller.populateInstance(mockInstance);
        }
        return mockInstance;
    }

    protected Object populateInstance(Object mockInstance, ClassWarp classWarp, Object value) throws Throwable {
        Field field = classWarp.getField();
        for (AttributeFiller attributeFiller : attributeFillers) {
            if (attributeFiller.match(classWarp)) {
                attributeFiller.populateInstance(mockInstance, field, value);
            }
        }
        return mockInstance;
    }
}
