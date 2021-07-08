package io.github.thebesteric.framework.mocker.unit.test;

import lombok.Getter;
import lombok.Setter;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * MockInstance
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-06-02 16:17
 * @since 1.0
 */
@Getter
@Setter
public class MockInstance {

    private Object instance;
    private Class<?> clazz;
    private MethodHolder methodHolder;
    private Queue<Object> returnValues;
    private Throwable returnThrowable;
    private MockerInterceptor mockerInterceptor;
    private boolean executedWhen = false;

    public synchronized void setReturnValue(Object returnValue) {
        if (returnValues == null) {
            returnValues = new ConcurrentLinkedQueue<>();
        }
        returnValues.offer(returnValue);
    }

    public synchronized Object getReturnValue() {
        Object obj = null;
        if (returnValues != null) {
            obj = returnValues.poll();
        }
        return obj;
    }
}
