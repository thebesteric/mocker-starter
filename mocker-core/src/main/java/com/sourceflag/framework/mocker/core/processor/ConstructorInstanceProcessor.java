package com.sourceflag.framework.mocker.core.processor;

import com.sourceflag.framework.mocker.annotation.MockIt;
import com.sourceflag.framework.mocker.core.filler.AttributeFiller;
import com.sourceflag.framework.mocker.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

/**
 * ConstructorInstanceProcessor
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-28 22:22
 * @since 1.0
 */
@RequiredArgsConstructor
public class ConstructorInstanceProcessor implements InstanceProcessor {

    private final List<AttributeFiller> attributeFillers;

    @Override
    public boolean match(MockIt mockIt) {
        return true;
    }

    @Override
    public Object doProcess(MockIt mockIt, Method method) throws Throwable {
        // instantiation mock instance
        Constructor<?> constructor = determineConstructor(method.getReturnType());
        Object mockInstance = newInstance(constructor);

        // initialization mock instance
        for (AttributeFiller attributeFiller : attributeFillers) {
            attributeFiller.fill(mockInstance);
        }
        return mockInstance;
    }

    private Constructor<?> determineConstructor(Class<?> clazz) {
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

    private Object newInstance(Constructor<?> constructor) throws Exception {
        Parameter[] parameters = constructor.getParameters();
        Object[] args = new Object[parameters.length];
        for (int i = 0; i < args.length; i++) {
            args[i] = ObjectUtils.initialValue(parameters[i].getType());
        }
        return constructor.newInstance(args);
    }
}
