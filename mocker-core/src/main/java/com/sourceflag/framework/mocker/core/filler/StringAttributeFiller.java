package com.sourceflag.framework.mocker.core.filler;

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
    public void doFill(Object mockInstance, Field field) throws Exception {
        Class<?> fieldClassType = field.getType();
        if (fieldClassType == String.class) {
            field.set(mockInstance, RandomStringUtils.randomAlphanumeric(RANDOM.nextInt(7) + 4));
        }
    }
}
