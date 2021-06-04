package test.demo;

/**
 * UserService
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-06-02 15:54
 * @since 1.0
 */
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public String sayHello(String name) {
        return userDao.sayHello(name);
    }

    public int count() {
        return 10;
    }

}
