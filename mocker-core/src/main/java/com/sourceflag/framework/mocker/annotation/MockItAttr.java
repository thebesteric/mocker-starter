package com.sourceflag.framework.mocker.annotation;

import com.sourceflag.framework.mocker.core.filler.ComplexAttributeFiller;

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

    Class<?> clazz() default ComplexAttributeFiller.NonValue.class;

}
