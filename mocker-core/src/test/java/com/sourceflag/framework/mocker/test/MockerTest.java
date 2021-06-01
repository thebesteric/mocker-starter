package com.sourceflag.framework.mocker.test;

import com.sourceflag.framework.mocker.test.demo.TestController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * ScannerTest
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-25 17:35
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Mocker Test Case")
public class MockerTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    @DisplayName("testNormal")
    public void testNormal() {
        TestController testController = applicationContext.getBean(TestController.class);
        System.out.println(testController.normal());
        System.out.println(testController.normal());
    }

    @Test
    @DisplayName("testMock")
    public void testMock() {
        TestController testController = applicationContext.getBean(TestController.class);
        System.out.println(testController.mock());
        System.out.println(testController.mock());
    }

    @Test
    @DisplayName("testLocalTarget")
    public void testLocalTarget() {
        TestController testController = applicationContext.getBean(TestController.class);
        System.out.println(testController.localTarget());
        System.out.println(testController.localTarget());
    }

    @Test
    @DisplayName("testRemoteTarget")
    public void testRemoteTarget() {
        TestController testController = applicationContext.getBean(TestController.class);
        System.out.println(testController.remoteTarget());
        System.out.println(testController.remoteTarget());
    }

    @Test
    @DisplayName("testConfigSimple")
    public void testConfigSimple() {
        TestController testController = applicationContext.getBean(TestController.class);
        System.out.println(testController.configSimple());
        System.out.println(testController.configSimple());
    }

    @Test
    @DisplayName("testConfigComplex")
    public void testConfigComplex() {
        TestController testController = applicationContext.getBean(TestController.class);
        System.out.println(testController.configComplex());
        System.out.println(testController.configComplex());
    }

}
