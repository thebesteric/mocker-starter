package io.github.thebesteric.framework.mocker.unit.test.demo;

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
    @MockProp({"123", "456", "789"})
    private String id;

    @MockProp({"name-1", "name-2", "name-3"})
    private String name;
}
