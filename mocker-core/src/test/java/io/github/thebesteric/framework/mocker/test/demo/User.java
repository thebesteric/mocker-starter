package io.github.thebesteric.framework.mocker.test.demo;

import io.github.thebesteric.framework.mocker.annotation.MockIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-25 17:55
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class User extends HashMap<String, String> {
    private String username;
    private String password;
    @MockIgnore
    private Integer age;
    private double x;
    private Float y;
    private User u;
    private Order o;
    private String[] arr;
    private Order[] orders;
    private List<Integer> list;
    private List<Order> listOrders;
    private Map<String, String> maps;
    private Map<String, Order> mapOrders;

    public User(String username, String password, int age) {
        this.username = username;
        this.password = password;
        this.age = age;
    }
}
