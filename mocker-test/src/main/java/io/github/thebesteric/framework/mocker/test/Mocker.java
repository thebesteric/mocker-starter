package io.github.thebesteric.framework.mocker.test;

import io.github.thebesteric.framework.mocker.commons.utils.ReflectUtils;
import io.github.thebesteric.framework.mocker.test.exception.InvalidOperationException;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Mocker
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-06-02 15:28
 * @since 1.0
 */
public class Mocker {


    private static Mocker mocker;

    public static ThreadLocal<MockInstance> threadLocal = new ThreadLocal<>();

    private Mocker(Class<?> mockClass) {
        MockInstance mockInstance = new MockInstance();
        mockInstance.setClazz(mockClass);
        threadLocal.set(mockInstance);
    }

    @SuppressWarnings("unchecked")
    public static <T> T mock(Class<T> mockClass) {
        mocker = new Mocker(mockClass);
        threadLocal.get().setMockerInterceptor(new MockerInterceptor());
        threadLocal.get().setInstance(enhance(mockClass, threadLocal.get().getMockerInterceptor()));
        return (T) threadLocal.get().getInstance();
    }

    public static Mocker when(Object invoker) {
        if (threadLocal.get() == null) {
            throw new InvalidOperationException("please execute \"%s\" first", "mock");
        }
        threadLocal.get().setExecutedWhen(true);
        return mocker;
    }

    public Mocker thenReturn(Object returnValue) {
        checkWhenStep();
        threadLocal.get().setReturnValue(returnValue);
        return mocker;
    }

    public void thenThrow(Throwable throwable) {
        checkWhenStep();
        threadLocal.get().setReturnThrowable(throwable);
    }

    private void checkWhenStep() {
        if (!threadLocal.get().isExecutedWhen()) {
            throw new InvalidOperationException("please execute \"%s\" first", "when");
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T enhance(Class<T> mockClass, MethodInterceptor interceptor) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(mockClass);
        enhancer.setCallback(interceptor);
        if (mockClass.isInterface()) {
            return (T) enhancer.create();
        }
        Constructor<?> constructor = ReflectUtils.determineConstructor(mockClass);
        Class<?>[] argumentTypes = ReflectUtils.argumentTypes(constructor);
        if (argumentTypes != null) {
            Object[] argumentValues = ReflectUtils.argumentValues(constructor);
            return (T) enhancer.create(argumentTypes, argumentValues);
        }
        return (T) enhancer.create();
    }

}
