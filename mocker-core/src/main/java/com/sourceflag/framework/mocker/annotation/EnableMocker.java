package com.sourceflag.framework.mocker.annotation;

import com.sourceflag.framework.mocker.configuration.MockerMarker;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * EnableMocker
 *
 * @author Eric
 * @date 2021/5/25 15:19
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(MockerMarker.class)
@Documented
public @interface EnableMocker {
}
