package com.ydekor.diplomback.web.dto;


import com.ydekor.diplomback.exception.BaseException;
import lombok.Getter;

public class ResponseError {

    @Getter
    private final String description;

    public ResponseError(BaseException ex) {
        this.description = ex.getMessage();
    }

}
