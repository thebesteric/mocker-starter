package io.github.thebesteric.framework.mocker.core.processor;

import io.github.thebesteric.framework.mocker.annotation.MockIt;
import io.github.thebesteric.framework.mocker.commons.utils.CacheUtils;

import java.lang.reflect.Method;

/**
 * Mock 实例
 *
 * @author Eric
 * @date 2021/5/28 18:45
 */
public interface InstanceProcessor {

    int MOCK_PROCESSOR_ORDER = 1;
    int LOCAL_PROCESSOR_ORDER = 2;
    int REMOTE_PROCESSOR_ORDER = 3;
    int CONFIG_PROCESSOR_ORDER = 4;

    /**
     * 是否匹配
     *
     * @param mockIt mockIt
     * @return boolean
     * @author Eric
     * @date 2021/5/28 23:56
     */
    boolean match(MockIt mockIt);

    /**
     * 执行处理
     *
     * @param mockIt mockIt
     * @param method method
     * @return java.lang.Object
     * @throws Throwable throwable
     * @author Eric
     * @date 2021/5/28 23:56
     */
    default Object process(MockIt mockIt, Method method) throws Throwable {
        String methodSignature = method.getDeclaringClass().getName() + "." + method.getName();
        Object mockInstance;
        if (mockIt.cache()) {
            mockInstance = CacheUtils.get(methodSignature);
            if (mockInstance != null) {
                return mockInstance;
            }
        }
        mockInstance = doProcess(mockIt, method);
        if (mockInstance != null && mockIt.cache()) {
            CacheUtils.put(methodSignature, mockInstance);
        }
        return mockInstance;
    }

    /**
     * 执行处理
     *
     * @param mockIt mockIt
     * @param method method
     * @return java.lang.Object
     * @throws Throwable throwable
     * @author Eric
     * @date 2021/5/28 23:57
     */
    Object doProcess(MockIt mockIt, Method method) throws Throwable;

    /**
     * 排序
     *
     * @return int
     * @author Eric
     * @date 2021/5/28 23:59
     */
    default int order() {
        return Integer.MAX_VALUE;
    }

}
