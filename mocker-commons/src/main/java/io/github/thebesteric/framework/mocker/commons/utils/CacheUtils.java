package io.github.thebesteric.framework.mocker.commons.utils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.TimeUnit;

/**
 * CacheUtils
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-28 14:14
 * @since 1.0
 */
public class CacheUtils {

    public Cache<Object, Object> cache;

    public CacheUtils() {
        this(CacheConfiguration.builder().build());
    }

    public CacheUtils(CacheConfiguration configuration) {
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder().softValues();
        if (configuration.getExpireAfterWrite() > 0) {
            caffeine.expireAfterWrite(configuration.getExpireAfterWrite(), TimeUnit.SECONDS);
        }
        if (configuration.getExpireAfterAccess() > 0) {
            caffeine.expireAfterAccess(configuration.getExpireAfterAccess(), TimeUnit.SECONDS);
        }
        cache = caffeine.build();
    }

    public void put(Object key, Object value) {
        cache.put(key, value);
    }

    public Object get(Object key) {
        return cache.getIfPresent(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Object key, Class<T> clazz) {
        return (T) cache.getIfPresent(key);
    }

    public void clean() {
        cache.invalidateAll();
    }

    @Builder
    @Setter
    @Getter
    public static class CacheConfiguration {
        /** 最后一次写操作后经过指定时间过期 */
        @Builder.Default
        private int expireAfterWrite = 600;

        /** 最后一次读或写操作后经过指定时间过期 */
        @Builder.Default
        private int expireAfterAccess = 600;
    }

}
