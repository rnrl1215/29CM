package com.cm.order.exception;

public class NotEnoughStockException extends OrderException {
    public NotEnoughStockException() {
        super(ExceptionType.NOT_ENOUGHT_STOCK.getMessage());
    }
}
