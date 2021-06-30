package io.github.thebesteric.framework.mocker.core.processor;

import io.github.thebesteric.framework.mocker.annotation.MockIt;
import io.github.thebesteric.framework.mocker.commons.exception.JsonParseException;
import io.github.thebesteric.framework.mocker.commons.utils.HttpUtils;
import io.github.thebesteric.framework.mocker.commons.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;

import java.lang.reflect.Method;

/**
 * TargetInstanceProcessor
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-28 22:30
 * @since 1.0
 */
public class RemoteTargetInstanceProcessor extends AbstractTargetInstanceProcessor {

    private final HttpUtils httpUtils = HttpUtils.getInstance();

    @Override
    public boolean match(MockIt mockIt) {
        String target = mockIt.target();
        return !StringUtils.isEmpty(target) && (remoteProtocol(target) != null);
    }

    @Override
    public Object doProcess(MockIt mockIt, Method method) throws Throwable {
        String target = mockIt.target();
        String protocol = remoteProtocol(target);
        if (StringUtils.isNotEmpty(protocol)) {
            HttpUtils.ResponseEntry responseEntry = httpUtils.doGet(target);
            if (responseEntry.getCode() == HttpStatus.SC_OK) {
                String httpStr = responseEntry.getHttpStr();
                Class<?> returnType = method.getReturnType();
                try {
                    return JsonUtils.objectMapper.readValue(JsonUtils.toJsonStr(httpStr), returnType);
                } catch (Throwable throwable) {
                    throw new JsonParseException(httpStr + " cannot cast to " + returnType.getName());
                }
            }
        }
        return null;
    }

    @Override
    public int order() {
        return InstanceProcessor.REMOTE_PROCESSOR_ORDER;
    }

}
