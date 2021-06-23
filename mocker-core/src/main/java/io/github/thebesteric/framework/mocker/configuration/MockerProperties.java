package io.github.thebesteric.framework.mocker.configuration;

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

    /** 总开关 */
    private boolean enable = true;

    /** 嵌套父子对象递归次数 */
    private int revisionTimes = 1;

    /** 列表长度 */
    private int iteratorLength = 3;

}
