package io.github.thebesteric.framework.mocker.commons.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ThreadLocalUtils {

    private static ThreadLocal<Map<Object, Object>> RESOURCES;

    public static void initThreadLocal() {
        RESOURCES = new ThreadLocal<>();
        RESOURCES.set(new HashMap<>());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void initInheritableThreadLocal() {
        removeResources();
        RESOURCES = new InheritableThreadLocal();
        RESOURCES.set(new HashMap<>());
    }

    public static void withInitial() {
        removeResources();
        RESOURCES = ThreadLocal.withInitial(HashMap::new);
    }

    public static void removeResources() {
        if (Objects.nonNull(RESOURCES)) {
            RESOURCES.remove();
        }
    }

    public static Map<Object, Object> getResources() {
        if (Objects.isNull(RESOURCES)) {
            initThreadLocal();
        }
        return RESOURCES.get();
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
        if (RESOURCES != null) {
            RESOURCES.get().clear();
        }
    }
}
