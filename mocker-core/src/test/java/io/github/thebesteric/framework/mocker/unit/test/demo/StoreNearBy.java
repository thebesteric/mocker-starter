package io.github.thebesteric.framework.mocker.unit.test.demo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StoreNearBy extends StoreBase {

    /** 是否已加购门店：0：否，1：是 */
    private Integer preference;

}