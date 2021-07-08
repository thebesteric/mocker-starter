package io.github.thebesteric.framework.mocker.unit.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.thebesteric.framework.mocker.commons.utils.JsonUtils;
import io.github.thebesteric.framework.mocker.unit.test.demo.TestController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.TimeUnit;

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
@DisplayName("Mocker Core Test Case")
public class MockerCoreTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    @DisplayName("testNormal")
    public void testNormal() throws JsonProcessingException {
        TestController testController = applicationContext.getBean(TestController.class);
        System.out.println(JsonUtils.toJsonStr(testController.normal()));
    }

    @Test
    @DisplayName("testMock")
    public void testMock() throws JsonProcessingException {
        TestController testController = applicationContext.getBean(TestController.class);
        System.out.println(JsonUtils.toJsonStr(testController.mock()));
    }

    @Test
    @DisplayName("testLocalTarget")
    public void testLocalTarget() throws JsonProcessingException {
        TestController testController = applicationContext.getBean(TestController.class);
        System.out.println(JsonUtils.toJsonStr(testController.localTarget()));
    }

    @Test
    @DisplayName("testRemoteTarget")
    public void testRemoteTarget() throws JsonProcessingException {
        TestController testController = applicationContext.getBean(TestController.class);
        System.out.println(JsonUtils.toJsonStr(testController.remoteTarget()));
    }

    @Test
    @DisplayName("testConfigSimple")
    public void testConfigSimple() throws JsonProcessingException {
        TestController testController = applicationContext.getBean(TestController.class);
        System.out.println(JsonUtils.toJsonStr(testController.configSimple()));
    }

    @Test
    @DisplayName("testConfigComplex")
    public void testConfigComplex() throws JsonProcessingException {
        TestController testController = applicationContext.getBean(TestController.class);
        System.out.println(JsonUtils.toJsonStr(testController.configComplex()));
    }

    @Test
    @DisplayName("testConfigComplexArray")
    public void testConfigComplexArray() throws JsonProcessingException {
        TestController testController = applicationContext.getBean(TestController.class);
        System.out.println(JsonUtils.toJsonStr(testController.configComplexArray()));
    }

    @Test
    @DisplayName("testConfigDifficult")
    public void testConfigDifficult() throws JsonProcessingException {
        TestController testController = applicationContext.getBean(TestController.class);
        System.out.println(JsonUtils.toJsonStr(testController.configDifficult()));
    }

    @Test
    @DisplayName("testCache")
    public void testCache() throws Exception {
        TestController testController = applicationContext.getBean(TestController.class);
        System.out.println(JsonUtils.toJsonStr(testController.mock()));
        TimeUnit.SECONDS.sleep(10);
        System.out.println(JsonUtils.toJsonStr(testController.mock()));
    }

}
