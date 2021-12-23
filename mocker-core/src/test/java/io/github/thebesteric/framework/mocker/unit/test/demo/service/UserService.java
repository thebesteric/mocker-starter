package io.github.thebesteric.framework.mocker.unit.test.demo.service;

import io.github.thebesteric.framework.mocker.annotation.MockIt;
import io.github.thebesteric.framework.mocker.annotation.Mocker;
import io.github.thebesteric.framework.mocker.unit.test.demo.User;
import org.springframework.stereotype.Service;

@Service
@Mocker
public class UserService {

    @MockIt(target = "local://demo/user.json")
    public User getUser(String id) {
        System.out.println("i'm not executing...");
        return new User();
    }
}
