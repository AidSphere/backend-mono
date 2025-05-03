package org.spring.authenticationservice.exception;

public class UserAlreadyExistedException extends RuntimeException {
    public UserAlreadyExistedException(String message) {
        super(message);
    }

    public UserAlreadyExistedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistedException(Throwable cause) {
        super(cause);
    }
}
