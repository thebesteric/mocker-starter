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

    /** 本地 mock 路径前缀 */
    private String localPath = "/mock";

    /** 缓存设置 */
    private Cache cache = new Cache();

    @Getter
    @Setter
    public static class Cache {
        /** 最后一次写操作后经过指定时间过期 */
        private int expireAfterWrite = 600;

        /** 最后一次读或写操作后经过指定时间过期 */
        private int expireAfterAccess = 600;
    }

}
