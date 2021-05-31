package com.sourceflag.framework.mocker.annotation;

import org.springframework.core.annotation.AliasFor;

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

    @AliasFor(annotation = MockItAttr.class)
    String value() default "";

    @AliasFor(annotation = MockItAttr.class)
    String key() default "";

    @AliasFor(annotation = MockItAttr.class)
    String clazz() default "";

    MockItAttr[] attrs() default {};
}
