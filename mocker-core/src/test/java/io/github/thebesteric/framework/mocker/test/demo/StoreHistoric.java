package io.github.thebesteric.framework.mocker.test.demo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * StoreHistoric
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-06-22 16:06
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StoreHistoric extends StoreBase {
    private String username;
}
