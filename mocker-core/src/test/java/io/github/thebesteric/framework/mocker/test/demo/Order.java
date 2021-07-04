package io.github.thebesteric.framework.mocker.test.demo;

import io.github.thebesteric.framework.mocker.annotation.MockProp;
import lombok.Data;

/**
 * Order
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-06-10 01:43
 * @since 1.0
 */
@Data
public class Order {
    private Integer id;
    @MockProp("my-order")
    private String name;
}
