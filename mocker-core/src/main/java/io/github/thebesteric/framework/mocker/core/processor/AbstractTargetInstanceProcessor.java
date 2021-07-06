package io.github.thebesteric.framework.mocker.core.processor;

import io.github.thebesteric.framework.mocker.commons.utils.CacheUtils;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractTargetInstanceProcessor extends AbstractInstanceProcessor {

    protected static final String LOCAL_PROTOCOL = "local://";
    protected static final List<String> REMOTE_PROTOCOL = Arrays.asList("http://", "https://");

    public AbstractTargetInstanceProcessor(CacheUtils cacheUtils) {
        super(cacheUtils);
    }

    protected String remoteProtocol(String target) {
        for (String protocol : REMOTE_PROTOCOL) {
            if (target.startsWith(protocol)) {
                return protocol;
            }
        }
        return null;
    }
}
