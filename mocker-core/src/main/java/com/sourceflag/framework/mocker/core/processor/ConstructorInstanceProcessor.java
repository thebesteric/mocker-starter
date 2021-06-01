package com.sourceflag.framework.mocker.core.processor;

import com.sourceflag.framework.mocker.annotation.MockIt;
import com.sourceflag.framework.mocker.core.filler.AttributeFiller;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

/**
 * ConstructorInstanceProcessor
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-28 22:22
 * @since 1.0
 */
public class ConstructorInstanceProcessor extends AbstractConstructorInstanceProcessor {

    public ConstructorInstanceProcessor(List<AttributeFiller> attributeFillers) {
        super(attributeFillers);
    }

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
        return populateInstance(mockInstance);
    }
}
