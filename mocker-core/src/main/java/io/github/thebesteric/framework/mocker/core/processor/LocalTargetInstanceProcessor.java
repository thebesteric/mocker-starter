package io.github.thebesteric.framework.mocker.core.processor;

import io.github.thebesteric.framework.mocker.annotation.MockIt;
import io.github.thebesteric.framework.mocker.commons.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

/**
 * LocalTargetInstanceProcessor
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-28 22:31
 * @since 1.0
 */
public class LocalTargetInstanceProcessor implements InstanceProcessor {

    private static final String LOCAL_PROTOCOL = "local://";

    @Override
    public boolean match(MockIt mockIt) {
        String target = mockIt.target();
        return !StringUtils.isEmpty(target) && target.startsWith(LOCAL_PROTOCOL);
    }

    @Override
    public Object doProcess(MockIt mockIt, Method method) throws Throwable {
        String target = mockIt.target();
        target = target.substring(LOCAL_PROTOCOL.length());
        Resource resource = new ClassPathResource(target);
        if (resource.exists()) {
            try (InputStreamReader isr = new InputStreamReader(resource.getInputStream())) {
                BufferedReader br = new BufferedReader(isr);
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }
                String mockStr = builder.toString();
                if (StringUtils.isNotEmpty(mockStr)) {
                    return JsonUtils.objectMapper.readValue(JsonUtils.toJsonStr(mockStr), method.getReturnType());
                }
            }
        }
        return null;
    }

    @Override
    public int order() {
        return 2;
    }
}
