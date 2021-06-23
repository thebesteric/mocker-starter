package io.github.thebesteric.framework.mocker.test;

import io.github.thebesteric.framework.mocker.commons.utils.ObjectUtils;
import io.github.thebesteric.framework.mocker.commons.utils.ReflectUtils;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * MockerWhenInterceptor
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-06-02 15:33
 * @since 1.0
 */
public class MockerInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        // If an exception is defined, it is thrown first
        MockInstance mockInstance = Mocker.threadLocal.get();
        Throwable returnThrowable = mockInstance.getReturnThrowable();
        if (returnThrowable != null) {
            throw returnThrowable;
        }

        MethodHolder methodHolder = mockInstance.getMethodHolder();
        if (mockInstance.getReturnValues() != null) {
            Object returnValue = mockInstance.getReturnValue();
            if (returnValue != null) {
                if (methodHolder.getMethodSignature().equals(ReflectUtils.getMethodSignature(method))) {
                    return returnValue;
                }
                return methodProxy.invokeSuper(obj, args);
            }
        }
        // when you execute the when method, it's going to go here
        return getEligibleReturnValue(method, args);
    }

    private Object getEligibleReturnValue(Method method, Object[] args) {
        MethodHolder methodHolder = new MethodHolder(method, args);
        Mocker.threadLocal.get().setMethodHolder(methodHolder);
        Class<?> returnType = method.getReturnType();
        return ObjectUtils.initialValue(returnType);
    }
}
