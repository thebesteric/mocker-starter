package io.github.thebesteric.framework.mocker.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * MockIt
 *
 * @author Eric
 * @date 2021/5/25 14:03
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MockIt {

    @AliasFor("enable")
    boolean value() default true;

    @AliasFor("value")
    boolean enable() default true;

    String mock() default "";

    String target() default "";

    boolean cache() default true;

    MockItResponse config() default @MockItResponse;

}
