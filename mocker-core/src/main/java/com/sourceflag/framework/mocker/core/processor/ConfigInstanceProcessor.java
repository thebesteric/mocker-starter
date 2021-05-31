package com.sourceflag.framework.mocker.core.processor;

import com.sourceflag.framework.mocker.annotation.MockIt;
import com.sourceflag.framework.mocker.annotation.MockItParam;
import com.sourceflag.framework.mocker.annotation.MockItResponse;

import java.lang.reflect.Method;

/**
 * ConfigInstanceProcessor
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-29 01:16
 * @since 1.0
 */
public class ConfigInstanceProcessor implements InstanceProcessor {

    @Override
    public boolean match(MockIt mockIt) {
        return mockIt.config().params().length > 0;
    }

    @Override
    public Object doProcess(MockIt mockIt, Method method) throws Throwable {
        MockItResponse mockItResponse = mockIt.config();
        MockItParam[] params = mockItResponse.params();
        return null;
    }

    @Override
    public int order() {
        return 3;
    }

}
