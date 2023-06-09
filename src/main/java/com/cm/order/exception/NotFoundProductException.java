package com.cm.order.exception;

public class NotFoundProductException extends OrderException {
    public NotFoundProductException() {
        super();
    }

    public NotFoundProductException(String message) {
        super(message);
    }

    public NotFoundProductException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundProductException(Throwable cause) {
        super(cause);
    }
}
