package io.github.thebesteric.framework.mocker.unit.test.demo;

import lombok.Data;

import java.util.List;

/**
 * User
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-25 17:55
 * @since 1.0
 */
@Data
public class MopStoreListResponse {
    private List<StoreNearBy> nearby;
    private List<Order> orders;
}
