package com.sourceflag.framework.mocker.utils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * CacheUtils
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-28 14:14
 * @since 1.0
 */
public class CacheUtils {

    public static Cache<Object, Object> CACHE;

    static {
        CACHE = Caffeine.newBuilder().softValues().build();
    }

    public static void put(Object key, Object value) {
        CACHE.put(key, value);
    }

    public static void clean() {
        CACHE.invalidateAll();
    }

    public static Object get(Object key) {
        return CACHE.getIfPresent(key);
    }

}
