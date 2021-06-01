package com.sourceflag.framework.mocker.core.processor;

import com.sourceflag.framework.mocker.core.filler.AttributeFiller;
import com.sourceflag.framework.mocker.utils.ObjectUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

/**
 * AbstractConstructorInstanceProcessor
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-31 23:09
 * @since 1.0
 */
public abstract class AbstractConstructorInstanceProcessor implements InstanceProcessor {

    protected final List<AttributeFiller> attributeFillers;

    public AbstractConstructorInstanceProcessor(List<AttributeFiller> attributeFillers) {
        this.attributeFillers = attributeFillers;
    }

    protected Constructor<?> determineConstructor(Class<?> clazz) {
        Constructor<?>[] rawCandidates = clazz.getDeclaredConstructors();
        List<Constructor<?>> constructors = Arrays.asList(rawCandidates);
        constructors.sort((o1, o2) -> {
            if (o1.getParameterCount() != o2.getParameterCount()) {
                return o1.getParameterCount() > o2.getParameterCount() ? 1 : -1;
            }
            return 0;
        });
        return constructors.get(0);
    }

    protected Object newInstance(Constructor<?> constructor) throws Throwable {
        constructor.setAccessible(true);
        Parameter[] parameters = constructor.getParameters();
        Object[] args = new Object[parameters.length];
        for (int i = 0; i < args.length; i++) {
            args[i] = ObjectUtils.initialValue(parameters[i].getType());
        }
        return constructor.newInstance(args);
    }

    protected Object populateInstance(Object mockInstance) throws Throwable {
        for (AttributeFiller attributeFiller : attributeFillers) {
            attributeFiller.populateInstance(mockInstance);
        }
        return mockInstance;
    }

    protected Object populateInstance(Object mockInstance, Field field, Object value) throws Throwable {
        for (AttributeFiller attributeFiller : attributeFillers) {
            if (attributeFiller.match(field)) {
                field.setAccessible(true);
                attributeFiller.populateInstance(mockInstance, field, value);
            }
        }
        return mockInstance;
    }
}
