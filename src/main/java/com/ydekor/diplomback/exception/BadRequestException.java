package com.ydekor.diplomback.exception;

public class BadRequestException extends BaseException {

    public BadRequestException(Object[] params) {
        super("Wrong attribute: %s", params);
    }

    public BadRequestException(String param) {
        this(new String[] {param});
    }
}
