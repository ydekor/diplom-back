package com.ydekor.diplomback.exception;

public class NotFoundException extends BaseException {
    public NotFoundException(String message) {
        super("[%s] not found", new String[]{message});
    }
}
