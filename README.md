### Quick Start

- Mocker Starter plugin based on SpringBoot

```java
@EnableMocker
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

- Use mocker at the Controller layer

```java
@RestController
@Mocker(enable=true)
public class UserController {
    
    // 动态生成 mock 数据
    @Getmapping("/getUser")
    @MockIt
    public User getUser(String name) {
        User user = userService.getUser(name);
        return user;
    }

    // 指定返回值格式
    @Getmapping("/getUser")
    @MockIt(mock="{key1: value2, key2: value2}", enable=false)
    public R getUser(String name){
        User user = userService.getUser(name);
        return R.success(user);
    }

    // 根据 target 属性，执行本地 json 文件地址
    @Getmapping("/getUser")
    @MockIt(target="local://demo/xxxxx.json", cache=false)
    public R getUser(String name){
        User user = userService.getUser(name);
        return R.success(user);
    }

    // 根据 target 属性，执行远程 json 文件地址
    @Getmapping("/getUser")
    @MockIt(target="https://xxxxx.json", cache=false)
    public R getUser(String name){
        User user = userService.getUser(name);
        return R.success(user);
    }

    // 配置方式：指定返回值类型，并指定返回值中相关属性对应的参数
    @Getmapping("/getUser")
    @MockIt(config=@MockItResponse(params={@MockItParam(key="data", clazz=User.class)}))
    public R getUser(String name){
        User user = userService.getUser(name);
        return R.success(user);
    }

    // 配置方式：指定返回值为数组类型，并指定返回值中相关属性对应的参数
    @Getmapping("/getUser")
    @MockIt(config=@MockItResponse(params={@MockItParam(key="data", clazz=User[].class)}))
    public R getUser(String name){
        User user = userService.getUser(name);
        return R.success(user);
    }

    // 配置方式：指定返回值类型，并指定返回值中多个相关属性对应的参数
    @Getmapping("/getUser")
    @MockIt(config=@MockItResponse(params={@MockItParam(key="data", clazz=User.class), 
                                         @MockItParam(key="code", value="200")}))
    public R getUser(String name){
        User user = userService.getUser(name);
        return R.success(user);
    }

    // 配置方式：指定返回值类型，并指定返回值中相关属性对应的参数，以及属性内对应的某个参数的值
    @Getmapping("/getUser")
    @MockIt(config=@MockItResponse(params={@MockItParam(key="data", clazz=User.class, 
                                                      attrs={@MockItAttr(key="username", value="eric"),
                                                            @MockItAttr(key="password", value="******")}), 
                                         @MockItParam(key="code", value="200")}))
    public R getUser(String name){
        User user = userService.getUser(name);
        return R.success(user);
    }
}
```

### Configure Mocker
```yaml
sourceflag:
  mocker:
    enable: true
    revision-times: 2
    local-path: "/mock"
```