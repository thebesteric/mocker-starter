package io.github.thebesteric.framework.mocker.test;

import io.github.thebesteric.framework.mocker.annotation.EnableMocker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * Application
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-25 18:17
 * @since 1.0
 */
@SpringBootApplication
@Configuration
@EnableMocker
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
