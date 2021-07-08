package io.github.thebesteric.framework.mocker.test.demo;

import io.github.thebesteric.framework.mocker.annotation.MockIgnore;
import io.github.thebesteric.framework.mocker.annotation.MockProp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
    @MockProp({"3", "4", "5", "6"})
    private Set<String> sets;
    @MockProp(length = 4)
    private Set<Order> orderSets;
    private double x = 1.11;
    @MockProp("3.1415926")
    private float y;
    @MockProp("3.1415926")
    private Double pi;
    private User u;
    private Order o;
    @MockProp({"1", "2"})
    private double[] arr;
    @MockProp(length = 2)
    private Order[] orders;
    @MockProp({"3", "4", "5", "6"})
    private List<Integer> list;

    @MockProp(length = 4)
    private List<Order> listOrders;
    private Map<String, String> maps;
    private Map<String, Order> mapOrders;

    public User(String username, String password, int age) {
        this.username = username;
        this.password = password;
        this.age = age;
    }
}
