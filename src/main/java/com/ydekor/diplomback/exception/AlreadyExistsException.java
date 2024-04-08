package com.ydekor.diplomback.exception;

public class AlreadyExistsException extends BaseException {

    public AlreadyExistsException(Object[] params) {
        super("Record with %s already exist", params);
    }
    public AlreadyExistsException(String param) {
        this(new String[] {param});
    }

}
