package com.sourceflag.framework.mocker.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * MockItResponse
 *
 * @author Eric
 * @date 2021/5/25 14:07
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MockItResponse {

    @AliasFor("params")
    MockItParam[] value() default {};

    @AliasFor("value")
    MockItParam[] params() default {};

}
