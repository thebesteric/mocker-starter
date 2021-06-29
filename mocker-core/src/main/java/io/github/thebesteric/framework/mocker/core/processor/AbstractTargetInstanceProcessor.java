package io.github.thebesteric.framework.mocker.core.processor;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractTargetInstanceProcessor implements InstanceProcessor {
    protected static final String LOCAL_PROTOCOL = "local://";
    protected static final List<String> REMOTE_PROTOCOL = Arrays.asList("http://", "https://");

    protected String remoteProtocol(String target) {
        for (String protocol : REMOTE_PROTOCOL) {
            if (target.startsWith(protocol)) {
                return protocol;
            }
        }
        return null;
    }
}
