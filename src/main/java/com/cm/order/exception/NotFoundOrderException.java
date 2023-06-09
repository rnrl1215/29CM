package com.cm.order.exception;

public class NotFoundOrderException extends OrderException {
    public NotFoundOrderException() {
        super(ExceptionType.NOT_FOUND_ORDER.getMessage());
    }
}
