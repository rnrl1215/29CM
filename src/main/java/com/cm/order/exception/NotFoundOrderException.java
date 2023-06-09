package com.cm.order.exception;

public class NotFoundOrderException extends OrderException {
    public NotFoundOrderException() {
        super();
    }

    public NotFoundOrderException(String message) {
        super(message);
    }

    public NotFoundOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundOrderException(Throwable cause) {
        super(cause);
    }
}
