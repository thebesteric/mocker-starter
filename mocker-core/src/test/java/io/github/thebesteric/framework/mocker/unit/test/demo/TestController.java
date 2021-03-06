package io.github.thebesteric.framework.mocker.unit.test.demo;

import io.github.thebesteric.framework.mocker.annotation.*;
import io.github.thebesteric.framework.mocker.unit.test.demo.domain.ProductDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * UserController
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-25 17:53
 * @since 1.0
 */
@Mocker(enable = true)
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController extends BaseController {

    private static final Map<Integer, User> USERS = new HashMap<>(16);

    static {
        USERS.put(1, new User("eric", "123456", 18));
        USERS.put(2, new User("lili", "654321", 20));
        USERS.put(3, new User("lucy", "888888", 19));
    }

    private final Environment env;

    @MockIt(enable = true, cache = true)
    @GetMapping("/normal")
    public ProductDetailResponse normal() {
        return null;
    }

    @MockIt(mock = "{username: eric, password: 123456}", cache = true)
    @GetMapping("/mock")
    public User mock() {
        return USERS.get(2);
    }

    @MockIt(target = "/user.json")
    @GetMapping("/localTarget")
    public User localTarget() {
        return USERS.get(3);
    }

    @MockIt(target = "https://yuanduan.oss-cn-hangzhou.aliyuncs.com/test/product-detail.json")
    @GetMapping("/remoteTarget")
    public Object remoteTarget() {
        return USERS.get(3);
    }

    @MockIt(config = @MockItResponse(params = {
            @MockItParam(key = "code", value = "201"),
            @MockItParam(key = "message", value = "OK")}))
    @GetMapping("/configSimple")
    public R configSimple() {
        return R.success();
    }

    @MockIt(config = @MockItResponse(params = {
            @MockItParam(key = "code", value = "201"),
            @MockItParam(key = "message", value = "OK"),
            @MockItParam(key = "data", clazz = User.class)}))
    @GetMapping("/configComplex")
    public R configComplex() {
        return R.success();
    }

    @MockIt(config = @MockItResponse(params = {
            @MockItParam(key = "code", value = "201"),
            @MockItParam(key = "message", value = "OK"),
            @MockItParam(key = "data", clazz = Order[].class)}))
    @GetMapping("/configComplexArray")
    public R configComplexArray() {
        return R.success();
    }

    @MockIt(config = @MockItResponse(params = {
            @MockItParam(key = "code", value = "201"),
            @MockItParam(key = "message", value = "OK"),
            @MockItParam(key = "data", clazz = User.class,
                    attrs = {@MockItAttr(key = "username", value = "me"),
                            @MockItAttr(key = "password", value = "****"),
                            @MockItAttr(key = "u", clazz = User.class)})}))
    @GetMapping("/configDifficult")
    public R configDifficult() {
        return R.success();
    }

}
