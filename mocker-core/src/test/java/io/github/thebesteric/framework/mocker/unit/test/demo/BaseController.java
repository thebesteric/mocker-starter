package io.github.thebesteric.framework.mocker.unit.test.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * BaseController
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-26 16:40
 * @since 1.0
 */
public class BaseController {

    @Autowired
    protected ApplicationContext context;

}
