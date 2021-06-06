package io.github.thebesteric.framework.mocker.core.filler;

import org.apache.commons.lang3.RandomStringUtils;

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
public class StringAttributeFiller implements AttributeFiller {

    @Override
    public boolean match(Field field) {
        return String.class == field.getType();
    }

    @Override
    public void doPopulateInstance(Object mockInstance, Field field, Object value) throws Throwable {
        // random 4 ~ 10 characters
        field.set(mockInstance, value != null ? value : RandomStringUtils.randomAlphanumeric(RANDOM.nextInt(7) + 4));
    }
}
