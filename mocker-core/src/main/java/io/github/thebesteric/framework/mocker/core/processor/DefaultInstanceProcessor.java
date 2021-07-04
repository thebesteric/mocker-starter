package io.github.thebesteric.framework.mocker.core.processor;

import io.github.thebesteric.framework.mocker.annotation.MockIt;
import io.github.thebesteric.framework.mocker.commons.utils.CacheUtils;
import io.github.thebesteric.framework.mocker.core.filler.AttributeFiller;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

/**
 * DefaultInstanceProcessor
 * <p>
 * Use the Constructor to create an instance
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-28 22:22
 * @since 1.0
 */
public class DefaultInstanceProcessor extends AbstractConstructorInstanceProcessor {

    public DefaultInstanceProcessor(CacheUtils cacheUtils, List<AttributeFiller> attributeFillers) {
        super(cacheUtils, attributeFillers);
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
