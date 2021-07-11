package io.github.thebesteric.framework.mocker.annotation;

import java.lang.annotation.*;

/**
 * MockProp
 * <p>
 * 作用于字段上，用于制定某些属性的值
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-07-04 15:55
 * @since 1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MockProp {
    String[] value() default {};
    int length() default -1;
    MockPropGroup[] group() default {};
}
