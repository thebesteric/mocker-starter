package com.sourceflag.framework.mocker.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MockerProperties
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-25 15:16
 * @since 1.0
 */
@Getter
@Setter
@ConfigurationProperties(prefix = MockerProperties.PROPERTIES_PREFIX)
public class MockerProperties {

    public static final String PROPERTIES_PREFIX = "sourceflag.mocker";

    private boolean enable = true;

}
