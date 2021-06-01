package com.sourceflag.framework.mocker.test.demo;

import lombok.Data;
import lombok.NoArgsConstructor;

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
public class User {
    private String username;
    private String password;
    private int age;
    private Double x;
    private Float y;
    private User u;

    public User(String username, String password, int age) {
        this.username = username;
        this.password = password;
        this.age = age;
    }
}
