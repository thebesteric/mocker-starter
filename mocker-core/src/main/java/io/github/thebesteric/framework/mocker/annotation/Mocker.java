package io.github.thebesteric.framework.mocker.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Mocker
 * <p>
 * 作用于类上，标识这个类为一个可以 mock 的类
 *
 * @author Eric
 * @date 2021/5/25 13:56
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Mocker {

    @AliasFor("enable")
    boolean value() default true;

    @AliasFor("value")
    boolean enable() default true;

}
