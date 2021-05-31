package com.sourceflag.framework.mocker.annotation;

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

    String value();

    Class<?> clazz();

}
