package io.github.thebesteric.framework.mocker.annotation;

import io.github.thebesteric.framework.mocker.core.filler.ComplexAttributeFiller;

import java.lang.annotation.*;

/**
 * MockItParam
 *
 * @author Eric
 * @date 2021/5/25 14:13
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MockItParam {

    String key();

    String value() default "";

    Class<?> clazz() default ComplexAttributeFiller.NonType.class;

    MockItAttr[] attrs() default {};
}
