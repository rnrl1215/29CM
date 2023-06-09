package kr.co._29cm.homework.exception;

import lombok.Getter;

@Getter
public enum ExceptionType {
    NOT_ENOUGHT_STOCK("Not enough stock"),
    NOT_FOUND_ORDER("Not found order"),
    NOT_FOUND_PRODUCT("Not found product");

    private final String message;

    ExceptionType(String message) {
        this.message = message;
    }
}
