package io.github.thebesteric.framework.mocker.commons.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ThreadLocalUtils {

    private static ThreadLocal<Map<Object, Object>> THREAD_LOCAL;


    public static void initThreadLocal() {
        if (THREAD_LOCAL == null) {
            THREAD_LOCAL = new ThreadLocal<>();
        }
        THREAD_LOCAL.set(new HashMap<>());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void initInheritableThreadLocal() {
        removeResources();
        THREAD_LOCAL = new InheritableThreadLocal();
        THREAD_LOCAL.set(new HashMap<>());
    }

    public static void withInitial() {
        removeResources();
        THREAD_LOCAL = ThreadLocal.withInitial(HashMap::new);
    }

    public static void removeResources() {
        if (THREAD_LOCAL.get() != null) {
            THREAD_LOCAL.remove();
        }
    }

    public static Map<Object, Object> getResources() {
        if (THREAD_LOCAL == null || THREAD_LOCAL.get() == null) {
            initThreadLocal();
        }
        return THREAD_LOCAL.get();
    }

    public static void put(Object key, Object value) {
        Objects.requireNonNull(key, "key cannot be null");
        getResources().put(key, value);
    }

    public static Object get(Object key) {
        return getResources().get(key);
    }

    public static Object getOrDefault(Object key, Object defaultValue) {
        Object value = getResources().get(key);
        return value != null ? value : defaultValue;
    }

    public static void remove(Object key) {
        getResources().remove(key);
    }

    public static void remove(Object... keys) {
        for (Object key : keys) {
            remove(key);
        }
    }

    public static void clear() {
        if (THREAD_LOCAL != null && THREAD_LOCAL.get() != null) {
            THREAD_LOCAL.get().clear();
        }
    }
}
