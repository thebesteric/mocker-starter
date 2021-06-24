package io.github.thebesteric.framework.mocker.test.demo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * StoreFavorite
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-06-22 12:06
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StoreFavorite extends StoreBase {

    /** 当日是否支持预约：0：不支持, 1：支持 */
    private Integer canTodayReservation;

    /** 隔日是否支持预约：0：不支持, 1：支持 */
    private Integer canFutureReservation;

    /** 是否已加购门店：0：否，1：是 */
    private Integer preference = 0;

}
