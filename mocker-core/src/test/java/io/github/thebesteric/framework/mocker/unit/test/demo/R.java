package io.github.thebesteric.framework.mocker.unit.test.demo;

import io.github.thebesteric.framework.mocker.annotation.MockIgnore;
import lombok.Builder;
import lombok.Data;

/**
 * R
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-31 23:02
 * @since 1.0
 */
@Data
@Builder
public class R {
    private int code;
    private Object data;
    private String message;
    @MockIgnore
    private Long timestamp;

    public static R success() {
        return R.builder().code(200).message("succeed").build();
    }

    public static R success(Object data) {
        return R.builder().code(200).message("succeed").data(data).build();
    }

}
