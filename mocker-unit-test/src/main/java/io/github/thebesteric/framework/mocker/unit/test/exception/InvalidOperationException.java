package io.github.thebesteric.framework.mocker.unit.test.exception;

/**
 * InvalidOperationException
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-06-02 16:42
 * @since 1.0
 */
public class InvalidOperationException extends RuntimeException {
    public InvalidOperationException(String message, Object... args) {
        super(String.format(message, args));
    }
}
