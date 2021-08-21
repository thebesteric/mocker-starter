package io.github.thebesteric.framework.mocker.unit.test.demo;

import io.github.thebesteric.framework.mocker.annotation.MockProp;
import io.github.thebesteric.framework.mocker.annotation.MockPropGroup;
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
    @MockProp({"123", "null", "789"})
    private String id;

    @MockProp({"name-1", "name-2", "name-3"})
    private String name;

    @MockProp(group = {
            @MockPropGroup({"pcms01_01", "pcms01_02"}),
            @MockPropGroup({"pcms02_01", "pcms02_02"}),
            @MockPropGroup({"pcms03_01", "pcms03_02"})
    })
    private String[] arr;
}
