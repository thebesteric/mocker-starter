package io.github.thebesteric.framework.mocker.core.processor;

import io.github.thebesteric.framework.mocker.annotation.MockIt;
import io.github.thebesteric.framework.mocker.commons.utils.JsonUtils;
import io.github.thebesteric.framework.mocker.configuration.MockerProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
@RequiredArgsConstructor
public class LocalTargetInstanceProcessor extends AbstractTargetInstanceProcessor {

    private final MockerProperties properties;

    @Override
    public boolean match(MockIt mockIt) {
        String target = mockIt.target();
        return !StringUtils.isEmpty(target) && remoteProtocol(target) == null;
    }

    @Override
    public Object doProcess(MockIt mockIt, Method method) throws Throwable {
        String target = mockIt.target().trim();
        String localPath = properties.getLocalPath();
        if (target.startsWith(LOCAL_PROTOCOL)) {
            target = target.substring(LOCAL_PROTOCOL.length());
        } else if (!StringUtils.isEmpty(localPath)) {
            if (localPath.endsWith("/") && target.startsWith("/")) {
                target = target.substring(1);
            }
            target = localPath + target;
        }
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
        throw new FileNotFoundException(target + " is not found");
    }

    @Override
    public int order() {
        return InstanceProcessor.LOCAL_PROCESSOR_ORDER;
    }
}
