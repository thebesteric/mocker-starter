### Mocker

```java
@EnableMocker
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

```java
@RestController
@Mocker(enable=true)
public class UserController {
    
    // 最简单的 mock，直接反射返回值，然后动态生成 mock 数据
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

    // 根据 target 属性，执行本地 json 文件地址，作为 mock 数据
    @Getmapping("/getUser")
    @MockIt(target="/xxxxx.json", cache=false)
    public R getUser(String name){
        User user = userService.getUser(name);
        return R.success(user);
    }

    // 根据 target 属性，执行远程 json 文件地址，作为 mock 数据
    @Getmapping("/getUser")
    @MockIt(target="https://xxxxx.json", cache=false)
    public R getUser(String name){
        User user = userService.getUser(name);
        return R.success(user);
    }

    // 配置方式：指定返回值类型，并指定返回值中相关属性对于的参数
    @Getmapping("/getUser")
    @MockIt(config=@MockItResponse(params={@MockItParam(key="data", clazz=User.class)}))
    public R getUser(String name){
        User user = userService.getUser(name);
        return R.success(user);
    }

    // 配置方式：指定返回值类型，并指定返回值中相关属性对于的参数
    @Getmapping("/getUser")
    @MockIt(config=@MockItResponse(params={@MockItParam(key="data", clazz=User.class), 
                                         @MockItParam(key="code", value="200")}))
    public R getUser(String name){
        User user = userService.getUser(name);
        return R.success(user);
    }

    // 配置方式：指定返回值类型，并指定返回值中相关属性对于的参数，以及参数属性内对应的值
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



