package io.github.thebesteric.framework.mocker.core.processor;

import io.github.thebesteric.framework.mocker.annotation.MockIt;
import io.github.thebesteric.framework.mocker.commons.utils.HttpUtils;
import io.github.thebesteric.framework.mocker.commons.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * TargetInstanceProcessor
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-28 22:30
 * @since 1.0
 */
public class RemoteTargetInstanceProcessor implements InstanceProcessor {

    private static final List<String> REMOTE_PROTOCOL = Arrays.asList("http://", "https://");

    private final HttpUtils httpUtils = HttpUtils.getInstance();

    @Override
    public boolean match(MockIt mockIt) {
        String target = mockIt.target();
        return !StringUtils.isEmpty(target) && (protocol(target) != null);
    }

    @Override
    public Object doProcess(MockIt mockIt, Method method) throws Throwable {
        String target = mockIt.target();
        String protocol = protocol(target);
        if (StringUtils.isNotEmpty(protocol)) {
            HttpUtils.ResponseEntry responseEntry = httpUtils.doGet(target);
            if (responseEntry.getCode() == HttpStatus.SC_OK) {
                return JsonUtils.objectMapper.readValue(JsonUtils.toJsonStr(responseEntry.getHttpStr()), method.getReturnType());
            }
        }
        return null;
    }

    @Override
    public int order() {
        return 2;
    }

    private String protocol(String target) {
        for (String protocol : REMOTE_PROTOCOL) {
            if (target.startsWith(protocol)) {
                return protocol;
            }
        }
        return null;
    }

}
