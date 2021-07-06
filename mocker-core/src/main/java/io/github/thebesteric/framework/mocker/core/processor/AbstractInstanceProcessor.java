package io.github.thebesteric.framework.mocker.core.processor;

import io.github.thebesteric.framework.mocker.annotation.MockIt;
import io.github.thebesteric.framework.mocker.commons.utils.CacheUtils;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;

@RequiredArgsConstructor
public abstract class AbstractInstanceProcessor implements InstanceProcessor {

    protected final CacheUtils cacheUtils;

    @Override
    public Object process(MockIt mockIt, Method method) throws Throwable {
        String methodSignature = method.getDeclaringClass().getName() + "." + method.getName();
        Object mockInstance;
        if (mockIt.cache()) {
            mockInstance = cacheUtils.get(methodSignature);
            if (mockInstance != null) {
                return mockInstance;
            }
        }
        mockInstance = doProcess(mockIt, method);
        if (mockInstance != null && mockIt.cache()) {
            cacheUtils.put(methodSignature, mockInstance);
        }
        return mockInstance;
    }

}
