package io.github.thebesteric.framework.mocker.test.demo;

import io.github.thebesteric.framework.mocker.annotation.MockIgnore;
import io.github.thebesteric.framework.mocker.annotation.MockProp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Getter
@Setter
@NoArgsConstructor
public class User {
    @MockProp("eric")
    private String username;
    @MockProp("888888")
    private String password;
    @MockIgnore
    private Integer age;
    private double x;
    @MockProp("3.1415926")
    private Float y;
    @MockProp("3.1415926")
    private Double pi;
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
