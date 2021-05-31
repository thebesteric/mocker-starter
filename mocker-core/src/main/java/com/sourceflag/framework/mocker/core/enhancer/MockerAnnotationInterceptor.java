package com.sourceflag.framework.mocker.core.enhancer;

import com.sourceflag.framework.mocker.annotation.MockIt;
import com.sourceflag.framework.mocker.core.processor.InstanceProcessor;
import com.sourceflag.framework.mocker.utils.JsonUtils;
import com.sourceflag.framework.mocker.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

/**
 * MockerInterceptor
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-26 15:34
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class MockerAnnotationInterceptor implements MethodInterceptor {

    private final List<InstanceProcessor> instanceProcessors;

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        MockIt mockIt = method.getDeclaredAnnotation(MockIt.class);
        if (mockIt != null && mockIt.enable()) {
            for (InstanceProcessor instanceProcessor : instanceProcessors) {
                if (instanceProcessor.match(mockIt)) {
                    Object mockInstance = instanceProcessor.process(mockIt, method);
                    if (mockInstance != null) {
                        return mockInstance;
                    }
                }
            }
        }
        return methodProxy.invokeSuper(obj, args);
    }

}
