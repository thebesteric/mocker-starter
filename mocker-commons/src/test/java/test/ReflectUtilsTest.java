package test;

import io.github.thebesteric.framework.mocker.commons.utils.ReflectUtils;
import org.junit.jupiter.api.Test;

/**
 * ReflectUtilsTest
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-06-04 17:08
 * @since 1.0
 */
public class ReflectUtilsTest {

    @Test
    public void getInterfaceImpls() {
        ReflectUtils.getInterfaceImpls(A.class, new String[]{"target\\test-classes"}).forEach((clazz) -> {
            System.out.println(clazz.getName());
        });
    }
}
