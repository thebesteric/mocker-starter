package io.github.thebesteric.framework.mocker.core.filler;

import io.github.thebesteric.framework.mocker.commons.utils.ReflectUtils;
import io.github.thebesteric.framework.mocker.core.domain.ClassWarp;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

/**
 * StringAttributeFiller
 * <p>
 * 字符串类型填充器
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-27 11:57
 * @since 1.0
 */
public class StringAttributeFiller extends AbstractAttributeFiller {

    @Override
    public boolean match(@NotNull ClassWarp classWarp) {
        return ReflectUtils.isString(classWarp.getClazz());
    }

    @Override
    public void doPopulateInstance(Object mockInstance, @NotNull Field field, Object value) throws Throwable {
        field.set(mockInstance, mockValue(field.getType(), mockInstance, value));
    }

    @Override
    public Object doMockValue(Class<?> clazz, Object instance, String value) {
        // random 4 ~ 10 characters
        return value != null ? value : RandomStringUtils.randomAlphanumeric(RANDOM.nextInt(7) + 4);
    }
}
