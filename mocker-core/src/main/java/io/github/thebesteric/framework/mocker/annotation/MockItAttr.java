package io.github.thebesteric.framework.mocker.annotation;

import io.github.thebesteric.framework.mocker.core.filler.ComplexAttributeFiller;

import java.lang.annotation.*;

/**
 * MockItAttr
 *
 * @author Eric
 * @date 2021/5/25 14:28
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MockItAttr {

    String key();

    String value() default "";

    Class<?> clazz() default ComplexAttributeFiller.NonType.class;

}
