package io.github.thebesteric.framework.mocker.core.domain;

import lombok.Data;

import java.lang.reflect.Field;

/**
 * ClassWarp
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-07-02 11:43
 * @since 1.0
 */
@Data
public class ClassWarp {
    private Field field;
    private Class<?> clazz;
    private boolean array;

    public ClassWarp(Field field) {
        this.field = field;
        this.clazz = field.getType();
    }

    public ClassWarp(Class<?> clazz) {
        this.clazz = clazz;
    }
}
