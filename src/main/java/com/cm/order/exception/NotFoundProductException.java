package com.cm.order.exception;

public class NotFoundProductException extends OrderException {
    public NotFoundProductException() {
        super(ExceptionType.NOT_FOUND_PRODUCT.getMessage());
    }
}
