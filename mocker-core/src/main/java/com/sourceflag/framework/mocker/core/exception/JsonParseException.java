package com.sourceflag.framework.mocker.core.exception;


import lombok.NoArgsConstructor;

/**
 * JsonParseException
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-05-28 16:09
 * @since 1.0
 */
@NoArgsConstructor
public class JsonParseException extends Exception {

    public JsonParseException(String message) {
        super(message);
    }

}
