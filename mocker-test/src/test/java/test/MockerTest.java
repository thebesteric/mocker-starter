package test;

import io.github.thebesteric.framework.mocker.test.Mocker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.demo.UserDao;
import test.demo.UserService;

import java.util.ArrayList;

/**
 * MockerTest
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-06-02 15:54
 * @since 1.0
 */

@DisplayName("Mocker Junit Test")
public class MockerTest {

    @Test
    public void mockObject() {
        UserDao mockUserDao = Mocker.mock(UserDao.class);
        Mocker.when(mockUserDao.sayHello("zs")).thenReturn("eric");
        UserService userService = new UserService(mockUserDao);
        Assertions.assertEquals("eric", userService.sayHello("zs"));

        UserService service =  Mocker.mock(UserService.class);
        Mocker.when(service.count()).thenReturn(100);
        System.out.println(service.count());
    }

    @Test
    public void mockList() {
        ArrayList<?> list = Mocker.mock(ArrayList.class);
        Mocker.when(list.get(0)).thenReturn("first");
        Assertions.assertEquals("first", list.get(0));

        Mocker.when(list.get(1)).thenReturn("second");
        Assertions.assertEquals("second", list.get(1));
    }

    @Test
    public void mockThrow() {
        ArrayList<?> list = Mocker.mock(ArrayList.class);
        Mocker.when(list.get(0)).thenThrow(new RuntimeException("mock throw"));
        Assertions.assertThrows(RuntimeException.class, ()-> list.get(0));
    }

}
