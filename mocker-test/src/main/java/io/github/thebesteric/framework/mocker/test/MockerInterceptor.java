package io.github.thebesteric.framework.mocker.test;

import lombok.Getter;
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

    @Getter
    private Method mockMethod;

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

        Throwable returnThrowable = Mocker.threadLocal.get().getReturnThrowable();
        if (returnThrowable != null) {
            throw returnThrowable;
        }

        Object returnValue = Mocker.threadLocal.get().getReturnValue();
        if (returnValue != null) {
            if (getMethodSignature(mockMethod).equals(getMethodSignature(method))) {
                return returnValue;
            }
            return methodProxy.invokeSuper(obj, args);
        }


        this.mockMethod = method;
        // TODO 反射方法返回值，随机给一个值
        return String.format("%s will be mock", method.getName());
    }

    public String getMethodSignature(Method method) {
        return method.getDeclaringClass().getName() + "." + method.getName();
    }
}
