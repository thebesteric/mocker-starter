package com.sourceflag.framework.mocker.core;

import com.sourceflag.framework.mocker.configuration.MockerProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * MockerCoreInitialization
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-25 17:05
 * @since 1.0
 */
@Slf4j
public class MockerCoreInitialization extends MockerInitialization {

    public MockerCoreInitialization(MockerProperties properties) {
        super(properties);
    }

    @Override
    public void start() {
        if (!properties.isEnable()) {
            log.info("MOCKER is disabled");
            return;
        }

        log.info("MOCKER is running");

        String projectPath = getProjectPath();
    }
}
