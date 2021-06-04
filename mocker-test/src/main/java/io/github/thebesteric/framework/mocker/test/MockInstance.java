package io.github.thebesteric.framework.mocker.test;

import lombok.Getter;
import lombok.Setter;

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
    private Object returnValue;
    private Throwable returnThrowable;

    private MockerInterceptor mockerInterceptor;

    private boolean executedWhen = false;
}
