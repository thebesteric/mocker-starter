package io.github.thebesteric.framework.mocker.core.processor;

import io.github.thebesteric.framework.mocker.annotation.MockIt;
import io.github.thebesteric.framework.mocker.commons.exception.JsonParseException;
import io.github.thebesteric.framework.mocker.commons.utils.CacheUtils;
import io.github.thebesteric.framework.mocker.commons.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;

/**
 * MockInstanceProcessor
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-28 18:50
 * @since 1.0
 */
public class MockInstanceProcessor extends AbstractInstanceProcessor {

    public MockInstanceProcessor(CacheUtils cacheUtils) {
        super(cacheUtils);
    }

    @Override
    public boolean match(MockIt mockIt) {
        return !StringUtils.isEmpty(mockIt.mock());
    }

    @Override
    public Object doProcess(MockIt mockIt, Method method) throws Throwable {
        String mockStr = mockIt.mock();
        if (!StringUtils.isEmpty(mockStr)) {
            try {
                return JsonUtils.objectMapper.readValue(JsonUtils.toJsonStr(mockStr), method.getReturnType());
            } catch (Throwable throwable) {
                throw new JsonParseException(String.format("Cannot parse %s to %s", mockStr, method.getReturnType().getName()));
            }
        }
        return null;
    }

    @Override
    public int order() {
        return InstanceProcessor.MOCK_PROCESSOR_ORDER;
    }
}
