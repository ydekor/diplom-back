package com.ydekor.diplomback.exception;

import java.util.Arrays;

public abstract class BaseException extends RuntimeException {

    public BaseException(String message, Object[] params) {
        super(String.format(message, Arrays.toString(params)));
    }
}
