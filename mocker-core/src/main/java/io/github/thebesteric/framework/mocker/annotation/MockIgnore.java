package io.github.thebesteric.framework.mocker.annotation;

import java.lang.annotation.*;

/**
 * MockIgnore
 * <p>
 * 作用于字段上，用于忽略某些属性
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-06-15 11:44
 * @since 1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MockIgnore {
}
